/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrievability;

import java.net.*;
import java.io.*;
import retrievability.UserQueryIndex;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import servelet.Jave_serv;
/**
 *
 * @author AYANAVA
 */
public class Try {
    
    static String indexDir = "C:\\Users\\AYANAVA\\Documents\\NetBeansProjects\\delete\\";

public static void main(String[]args)throws Exception
{
    //createIndex();
    UserQueryIndex.printing_indexes();
    
   
   
}


public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
		Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_CURRENT);
		boolean recreateIndexIfExists = true;
                Directory index=FSDirectory.open(new File(indexDir));
		IndexWriterConfig iwConf= new IndexWriterConfig(Version.LUCENE_CURRENT,analyzer);
                iwConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        //File dir = new File(FILES_TO_INDEX_DIRECTORY);
        //File[] files = dir.listFiles();
        //for (File file : files) {
        try (IndexWriter indexWriter = new IndexWriter(index,iwConf)) {
            //File dir = new File(FILES_TO_INDEX_DIRECTORY);
            //File[] files = dir.listFiles();
            //for (File file : files) {
            Document document = new Document();
            for(int i=1;i<10;i++)
            {
                String cited=Integer.toString(i);
                String citing=cited+"lol";
                
                //document.add(new StringField(cited,citing,Field.Store.YES));
                document.add(new Field("id",""+i,Field.Store.YES,Field.Index.ANALYZED));

		document.add(new Field("data",citing,Field.Store.YES,Field.Index.ANALYZED));
            }
            //document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));
            
            indexWriter.addDocument(document);
            //indexWriter.optimize();
            File indexDirectory = new File("C:\\Users\\AYANAVA\\Documents\\NetBeansProjects\\delete\\");
            IndexReader reader = IndexReader.open(FSDirectory.open(indexDirectory));
            IndexSearcher searcher = new IndexSearcher(reader);
                     
            Query query = new MatchAllDocsQuery();
            TopDocs hits = searcher.search(query, 200);
            
            for (int i = 0; i < hits.totalHits; i++) {
					Document doc = searcher.doc(hits.scoreDocs[i].doc); // get the next document
					String id = doc.get("id"); // get its id field
					String url = doc.get("data"); // get its path field
					System.out.println("Found in :: "+ id +" "+ url);
}
	}
}
}
