/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievability;

/**
 *
 * @author AYANAVA
 */

import java.net.*;
import java.io.*;
import java.util.Iterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import static org.apache.sis.internal.system.Semaphores.query;
import static retrievability.Try.indexDir;
import servelet.Jave_serv;

public class UserQueryIndex {
    
    static String query_entered;static String user_id;
    static String indexDir = "C:\\Users\\AYANAVA\\Documents\\NetBeansProjects\\delete\\";
    
    public static void indexing(String query, String user)throws Exception, CorruptIndexException, LockObtainFailedException, IOException
    {
        query_entered=query;user_id=user;
        System.out.println(query_entered+"\t"+user_id);
        
        Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);
	boolean recreateIndexIfExists = true;
        Directory index=FSDirectory.open(new File(indexDir));
	IndexWriterConfig iwConf= new IndexWriterConfig(Version.LUCENE_CURRENT,analyzer);
        iwConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        //File dir = new File(FILES_TO_INDEX_DIRECTORY);
        //File[] files = dir.listFiles();
        //for (File file : files) {
        
        IndexWriter indexWriter = new IndexWriter(index,iwConf);
        
        Document doc = new Document();
        
        doc.add(new Field("user_id",user_id,Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("search_query",query_entered,Field.Store.YES,Field.Index.ANALYZED));
        
        indexWriter.addDocument(doc);
        indexWriter.close();
        
}
    public static void printing_indexes()throws Exception
    {
        
        ScoreDoc[] hits = null;
        TopDocs topDocs = null;
        
        System.out.println("Searching for --- " );
        Directory index=FSDirectory.open(new File(indexDir));
	IndexReader indexReader = IndexReader.open(index);
	IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //Searcher searcher=new Searcher();
        Query queryDocid = new TermQuery(new Term("user_id", "ayaisthebest"));
	//Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);
	//QueryParser queryParser = new QueryParser(Version.LUCENE_3_0, user_id, analyzer);
	
        TopScoreDocCollector collectorDocSearch = TopScoreDocCollector.create(100, true);

        indexSearcher.search(queryDocid, collectorDocSearch);
        topDocs = collectorDocSearch.topDocs();
        hits = topDocs.scoreDocs;
        System.out.println(hits.length);
        
        if(hits == null) {
            System.out.println("Nothing found");
            //return -1;
        }
        else {
            
            for(int i=0;i<hits.length;i++)
            {
                System.out.println("printing ayaisthebest---"+hits[i]);
                int docId = hits[i].doc;
                Document d = indexSearcher.doc(docId);
                String prev_search=d.get("search_query");
                // do something with current hit
                System.err.println("docID:"+docId + d.get("search_query"));
                System.err.println("");
                
            }
            //return hits[0].doc;
        }
    }
}
        
        
        
        
    

