/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievability;

import servelet.Jave_serv;
import indexer.ClusteredTweetIndexer;
import indexer.TweetAnalyzerFactory;
import indexer.TweetIndexer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.AfterEffectB;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BasicModelBE;
import org.apache.lucene.search.similarities.DFRSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.NormalizationH1;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/**
 *
 * Builds up a set of queries by iterating over the vocabulary intersection
 * of the two indices. Does not take terms that are too infrequent.
 */

class SampledQuery {
    RetrievabilityFinder sampler;
    String content;
    Query lucquery;
    Analyzer analyzer;
    
    public SampledQuery(RetrievabilityFinder sampler, String content) {
        this.sampler = sampler;
        this.content = content;
        analyzer = TweetAnalyzerFactory.createAnalyzer(sampler.prop, true);
    }
    
    Query initQuery() {
        QueryParser parser = new QueryParser(Version.LUCENE_4_9, content, analyzer);
        lucquery = parser.createBooleanQuery(
                TweetIndexer.FIELD_ANALYZED_CONTENT,
                content,
                BooleanClause.Occur.SHOULD);
        return lucquery;
    }
    
    TopDocs execute(IndexSearcher searcher) throws Exception {
        TopScoreDocCollector collector = TopScoreDocCollector.create(sampler.getNumWanted(), true);
        searcher.search(this.lucquery, collector);
        return collector.topDocs();
    }
}

class ClusteredSampleQuery extends SampledQuery {

    static final float WHOLE_MATCH_WT = 0.4f;
    static final float DOCTYPE_MATCH_WT = 1 - WHOLE_MATCH_WT;
    
    public ClusteredSampleQuery(RetrievabilityFinder sampler, String content) {
        super(sampler, content);
    }
    
    @Override
    Query initQuery() {
        BooleanQuery lucquery = new BooleanQuery();
        String[] tokens = content.split("\\s+");
        for (String token : tokens) {
            TermQuery wholeMatchTermQry = new TermQuery(
                    new Term(TweetIndexer.FIELD_ANALYZED_CONTENT, token));
            wholeMatchTermQry.setBoost(WHOLE_MATCH_WT);
            lucquery.add(wholeMatchTermQry, BooleanClause.Occur.SHOULD);
            
            // For matching pure terms
            TermQuery typeMatchTermQry = new TermQuery(
                    new Term(ClusteredTweetIndexer.FIELD_CLASS_PURE, token));
            typeMatchTermQry.setBoost(DOCTYPE_MATCH_WT);
            lucquery.add(typeMatchTermQry, BooleanClause.Occur.SHOULD);
            
            // For matching mixed terms
            typeMatchTermQry = new TermQuery(
                    new Term(ClusteredTweetIndexer.FIELD_CLASS_CDMIX, token));
            typeMatchTermQry.setBoost(DOCTYPE_MATCH_WT);
            lucquery.add(typeMatchTermQry, BooleanClause.Occur.SHOULD);
        }
        
        this.lucquery = lucquery;
        return lucquery;        
    }    
}

class RetrievabilityScore implements Comparable<RetrievabilityScore> {
    String docName;
    int docId;
    int score;
    boolean codeMixed;

    public RetrievabilityScore(String docName, int docId, boolean codeMixed) {
        this.docName = docName;
        this.docId = docId;
        this.score = 1;
        this.codeMixed = codeMixed;
    }

    public RetrievabilityScore(String docName, int docId, boolean codeMixed, int score) {
        this.docName = docName;
        this.docId = docId;
        this.score = score;
        this.codeMixed = codeMixed;
        System.out.println(codeMixed+"codeMixed");
        System.out.println("score----"+score);
    }
    
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(docName)
                .append('\t')
                .append(docId)
                .append('\t')
                .append(codeMixed?"1":"0")
                .append('\t')
                .append(score)
                .append('\n');
        return buff.toString();
        
    }

    @Override
    public int compareTo(RetrievabilityScore that) { // ascending
        return this.score < that.score? -1 : this.score == that.score? 0 : 1;
    }
}

public class RetrievabilityFinder {

    Properties prop;
    
    static Map<Integer , String> hashed_values = new HashMap<Integer, String>();
    
    boolean combinedIndex;
    boolean clusteredRetrieval;
    
    IndexReader reader;  // the combined index to search
    IndexSearcher[] searcher;
    static String Final_Query="";
    static final Similarity[] simArray = {
            //new DefaultSimilarity(),
            new BM25Similarity(),
            //new LMDirichletSimilarity(),
            new LMJelinekMercerSimilarity(0.6f),
            new DFRSimilarity(new BasicModelBE(), new AfterEffectB(), new NormalizationH1()),
            //new IBSimilarity(new DistributionLL(), new LambdaDF(), new NormalizationH1())
    };
    
    static final Similarity[] sims = {
            //new DefaultSimilarity(),
            new BM25Similarity(),
            //new LMDirichletSimilarity(),
            new LMJelinekMercerSimilarity(0.6f),
            new DFRSimilarity(new BasicModelBE(), new AfterEffectB(), new NormalizationH1()),
            //new IBSimilarity(new DistributionLL(), new LambdaDF(), new NormalizationH1()),
            new MultiSimilarity(simArray)
    };
    
    static final String[] fileNames = {
            //"tfidf",
            "bm25",
            //"lm-dir",
            "lm-jm",
            "dfr",
            //"ib",
            "fusion"
    };
    
    // Retrievability parameters
    int nwanted;
    int rankCutoff;  // threshold
    String prop_path;
    // Hashmaps for accumulating retrievability scores
    RetrievabilityScore[][] retrScoresForAllSims;
    
    public RetrievabilityFinder(String Final)
    {
        Final_Query=Final;
        System.err.println(Final_Query);
        prop_path="C:\\Users\\AYANAVA\\Documents\\NetBeansProjects\\Web\\init.properties";
        

        try {
                RetrievabilityFinder sampler = new RetrievabilityFinder(prop_path,0);
            
                sampler.computeRetrievabilityScoresForAllSims();
                sampler.writeRetrievabilityScores();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public RetrievabilityFinder(String propFile,int i) throws Exception {
        prop = new Properties();
        prop.load(new FileReader(propFile));                
        
        combinedIndex = Boolean.parseBoolean(
                prop.getProperty("retrieve.combined_index", "true"));
        clusteredRetrieval = Boolean.parseBoolean(
                prop.getProperty("retrieve.cluster", "false"));
        
        nwanted = Integer.parseInt(prop.getProperty("retrievability.nretrieve", "100"));
        rankCutoff = Integer.parseInt(prop.getProperty("retrievability.c", "10"));        
    }

    int getNumWanted() { return nwanted; }
    
    ArrayList<String> collectTerms(String indexName) throws Exception {
        ArrayList<String> termArray = new ArrayList<>();
        
        File indexDir = new File(prop.getProperty(indexName));
        try (IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexDir))) {
            Terms terms = MultiFields.getTerms(indexReader, TweetIndexer.FIELD_ANALYZED_CONTENT);
            
            for (TermsEnum termsEnum = terms.iterator(TermsEnum.EMPTY); ; ) {
                
                BytesRef text = termsEnum.next();
                //System.err.println(text.utf8ToString());
                if (text == null)
                    break;
                termArray.add(text.utf8ToString());
            }
        }
        return termArray;
    }
    
    ArrayList<String> listIntersection(ArrayList<String> a, ArrayList<String> b) {
        int i, j;
        int alen = a.size(), blen = b.size();
        ArrayList<String> c = new ArrayList<>();
        
        for (i = 0, j = 0; i < alen && j < blen; ) {
            String str_a = a.get(i);
            String str_b = b.get(j);
            
            int cmp = str_a.compareTo(str_b);            
            if (cmp < 0) {
                i++;
            }
            else if (cmp > 0) {
                j++;
            }
            else {
                c.add(str_a);
                i++;
                j++;
            }
        }
        
        return c;
    }
    
    boolean allRomanCharacters(String word) {
        // remove hyphenated words as well...
        
        int len = word.length();
        
        for (int i=0; i < len; i++) {
            char ch = word.charAt(i);
            if (!((ch >= 'A' && ch <= 'Z') || (ch >='a' && ch <= 'z') || ch==' '))
                return false;
        }
        return true;
    }
    
    ArrayList<String> pruneList(ArrayList<String> vocabList) throws Exception {
        ArrayList<String> prunedList = new ArrayList<>();
        int minThresh = Integer.parseInt(prop.getProperty("sampling.df_threshold", "10"));
        
        File indexDir = new File(prop.getProperty("splitindex.pure"));
        try (IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexDir))) {
            for (String term : vocabList) {
                if (!allRomanCharacters(term))
                    continue;
                Term t = new Term(TweetIndexer.FIELD_ANALYZED_CONTENT, term);
                long cf = indexReader.totalTermFreq(t); // coll_freq(t)
                if (cf > minThresh) {
                    prunedList.add(term);
                }
            }
        }
        return prunedList;
    }
    
    ArrayList<String> getVocabIntersection() throws Exception {
        
        ArrayList<String> pureTerms = collectTerms("splitindex.pure");
        ArrayList<String> mixedTerms = collectTerms("splitindex.mixed");
        
        ArrayList<String> commonTerms = listIntersection(pureTerms, mixedTerms);
        commonTerms = pruneList(commonTerms);
        System.err.println(pureTerms.get(1)+" xxx "+mixedTerms.get(1)+" xxx "+commonTerms.size());
        return commonTerms;
    }
    
    ArrayList<String> loadVocab(String Final_Query) throws Exception {
        ArrayList<String> vocabList = new ArrayList<>();
        /*try (FileReader fr = new FileReader(vocabFile); 
                BufferedReader br = new BufferedReader(fr)) {
            String line;
          */  
            int counter=0;
            while (counter==0) {
                String[] tokens = Final_Query.split("\\t");
                //System.out.println(tokens);
                String qry = tokens[0]; // the next token is the coll_freq
                //System.out.println(qry);
                vocabList.add(qry);
                counter++;
                System.out.println("END OF 1ST ITERATION");
            }
            
        
        return vocabList;
    }
    
    ArrayList<SampledQuery> sampleQueries() throws Exception {        
        List<String> commonTerms;        
        FileWriter fw = null;
        BufferedWriter bw = null;

        ArrayList<SampledQuery> queries = new ArrayList<>();
        
        // For collecting cfs
        File indexDir = new File(prop.getProperty("splitindex.pure"));
        try (IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexDir))) {
            //File queryVocab = new File(prop.getProperty("sampling.outfile"));
            
            if (Final_Query==null) {
                System.out.println("Computing vocab intersection...");
                commonTerms = getVocabIntersection(); // Has both unigrams and bigrams
                fw = new FileWriter(Final_Query);
                bw = new BufferedWriter(fw);
            }
            else {
                System.out.println("Loading pre-saved vocab intersection...");
                System.out.println(Final_Query);
                commonTerms = loadVocab(Final_Query);  // saves the intersection computation step
            } 
            
            for (String term : commonTerms) {
                SampledQuery q = !clusteredRetrieval?
                        new SampledQuery(this, term) :
                        new ClusteredSampleQuery(this, term);
                
                Query lucQuery = q.initQuery();
                if (lucQuery != null)
                    queries.add(q);
                
                Term t = new Term(TweetIndexer.FIELD_ANALYZED_CONTENT, term);
                long cf = indexReader.totalTermFreq(t); // coll_freq(t)
                String lineToWrite = term + "\t" + cf + "\n";
                
                if (bw != null)
                {
                    System.err.println(lineToWrite);
                    bw.write(lineToWrite, 0, lineToWrite.length());
                }
            }
            
            if (bw!=null && fw!=null) {
                bw.close();
                fw.close();
            }
        }
        return queries;
    }
    
    void showTopDocs(String query, String simName, TopDocs topDocs) throws Exception {
        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docId = topDocs.scoreDocs[i].doc;
            Document doc = reader.document(docId);
            System.out.println(query + "\t" + (i+1) + "\t" +
                    doc.get(TweetIndexer.FIELD_CODEMIXED) + "\t" +
                    doc.get(TweetIndexer.FIELD_ID) + "\t" +
                    topDocs.scoreDocs[i].score + "\t" + simName);
        }
    }
    
    void computeRetrievabilityScores(int simIndex, TopDocs topDocs) throws Exception {

        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            
            int docId = topDocs.scoreDocs[i].doc;
            
            Document doc = reader.document(docId);
            String docName = doc.get(TweetIndexer.FIELD_ANALYZED_CONTENT);
            
            //System.out.println(docId+"----"+" "+docName);   //printing the DocID and the TweetID
            hashed_values.put(docId,docName);
            boolean codeMixFlag = Integer.parseInt(doc.get(TweetIndexer.FIELD_CODEMIXED))==1;
            
            // cut-off at rank rankCutoff
            if (i >= rankCutoff)
                continue;
            
            if (retrScoresForAllSims[simIndex][docId] == null)
                retrScoresForAllSims[simIndex][docId] = new RetrievabilityScore(docName, docId, codeMixFlag);
            else
                retrScoresForAllSims[simIndex][docId].score++;
        }
    }

    void initSearch() throws Exception {
        
        File indexDir = new File(
                clusteredRetrieval?
                    prop.getProperty("index.cluster"):
                    prop.getProperty("index"));
        System.out.println(indexDir);
        
        reader = DirectoryReader.open(FSDirectory.open(indexDir));
         
        int nDocs = reader.numDocs();        
        
        if (!combinedIndex) {  // Retrieve from two separate indices
            reader.close();
            
            // Open the two indices separately...
            File pureIndexDir = new File(prop.getProperty("splitindex.pure"));
            IndexReader pureReader = DirectoryReader.open(FSDirectory.open(pureIndexDir));
            File mixedIndexDir = new File(prop.getProperty("splitindex.mixed"));
            IndexReader mixedReader = DirectoryReader.open(FSDirectory.open(mixedIndexDir));
            
            reader = new MultiReader(pureReader, mixedReader);
        }
        
        searcher = new IndexSearcher[sims.length];
        for (int i = 0; i < sims.length; i++) {
            searcher[i] = new IndexSearcher(reader);
            searcher[i].setSimilarity(sims[i]);
        }

        retrScoresForAllSims = new RetrievabilityScore[sims.length][nDocs];
    }
    
    void computeRetrievabilityScoresForAllSims() throws Exception {
        // Init searcher objects
        
        initSearch();
        
        System.out.println("Generating query samples...");
        ArrayList<SampledQuery> queries = sampleQueries();
        
        System.out.println("Executing query samples...");
        int count = 0, nqueries = queries.size();
        
        for (SampledQuery query: queries) {
            if (count %1 == 0)
                    System.out.println("Executing query: " + query.content + " (" + count + " of " + nqueries + ")");
               
            
                for (int i = 0; i < sims.length; i++) {
                    TopDocs topDocs = query.execute(searcher[i]);
                    //System.out.println(" "+topDocs);
                // For debugging...
                // showTopDocs(query.content, fileNames[i], topDocs);
                        
                    computeRetrievabilityScores(i, topDocs);
                }
            count++;
        }
                
    }
    
    void writeRetrievabilityScores() throws Exception {
        String oDir = prop.getProperty("retrievability.outdir");

        // write the scores... <docid> \t <code mixed> \t <score>
        for (int i = 0; i < sims.length; i++) {
            FileWriter fw = new FileWriter(oDir + "/" + fileNames[i] + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (int j = 0; j < retrScoresForAllSims[i].length; j++) {
                if (retrScoresForAllSims[i][j] == null)
                    continue;
                bw.write(retrScoresForAllSims[i][j].toString());
            }
            
            bw.close();
            fw.close();
        }
        
        reader.close();
    }
   
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[1];
            System.out.println("Usage: java RetrievabilityFinder <prop-file>");
            args[0] = "init.properties";
        }

        try {
            RetrievabilityFinder sampler = new RetrievabilityFinder(args[0]);
            sampler.computeRetrievabilityScoresForAllSims();
            sampler.writeRetrievabilityScores();
            //Jave_serv obj=new Jave_serv((HashMap) hashed_values);
            //Jave_serv.func((HashMap) hashed_values);
           
            System.err.println("printed");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

public Map<Integer, String> Mapping()
{
    for (Map.Entry<Integer, String> entry : hashed_values.entrySet()) {
            //System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    return hashed_values;
}
    
}
