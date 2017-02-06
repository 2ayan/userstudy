/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;





import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NoSuchDirectoryException;

/**
 *
 * @author dwaipayan
 */
public class GetAllDocIds {

    File indexFile;
    String dumpPath;

    public GetAllDocIds() {
    }

    public GetAllDocIds(File indexFile, String dumpPath) {
        this.indexFile = indexFile;
        this.dumpPath = dumpPath;
    }

    
    public static void main(String[] args) throws IOException {

        //GetAllDocIds obj = new GetAllDocIds(new File("/c/codemixir/var/index/codemixed/index.mixed/"),
         //   "/c/codemixir/mixed.docids");
        GetAllDocIds obj = new GetAllDocIds(new File("/c/codemixir/var/index/codemixed/index.pure/"),
            "/c/codemixir/pure.docids");
        System.out.println("Writing docId from index: "+obj.indexFile.getAbsolutePath()+
            " in: "+obj.dumpPath);
        obj.getAllDocIds();
    }

    /**
     *
     * @throws IOException
     */
    public void getAllDocIds() throws IOException {

        FileWriter fileWritter = new FileWriter(dumpPath);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile))) {
            int maxDoc = reader.maxDoc();
            for (int i = 0; i < maxDoc; i++) {
                Document d = reader.document(i);
//                System.out.println(d.get(TrecCommonVariables.FIELD_ID));
                bufferWritter.write(d.get("id")+"\n");
            }
        }
        catch(NoSuchDirectoryException e) {
            System.err.println("NoSuchDirectory at: "+indexFile.getAbsolutePath());
//            e.printStackTrace();
        }
        bufferWritter.close();

    }
}