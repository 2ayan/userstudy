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

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import servelet.Jave_serv;

public class Pagination {
    
static HashMap <Integer, String> hashed_values=new HashMap<Integer, String>();
static int size;static int temp;
static List<String> list;
static int relevant_counter=0;

public static void getHashMap(HashMap map)throws Exception
{
    hashed_values=map;
    size=hashed_values.size();temp=1;
    
    
    list = new ArrayList<String>(map.values());
    //ten_at_a_time();
}
    public static HashMap<Integer, String> ten_at_a_time()
    {   
        System.out.println("size of number of entries of docs fetched--"+size);
        //System.err.println("value of temp--"+temp);
        
        HashMap <Integer, String> map=new HashMap<Integer, String>();
        
        while(temp<=size-1)
        {
            if(((temp%10)!=0)&&(temp!=size-1))
            {
                map.put(temp,list.get(temp));
                temp++;
            }
            else if((temp%10)==0)
            {
                
                
                map.put(temp,list.get(temp));
                temp++;
                return map;
            }
            else if(temp==size-1)
            {
                map.put(temp,list.get(temp));
                temp++;
                return map;
            }
            
        }
        return null;
    }
    
    public static int size_temp()
    {
        int sub=size-temp;
        //System.out.println(sub);
        if(sub==0)
        {relevant_counter=0;
            
        }
        else if(sub<10)
            ;//temp=1;
        return sub;
        
    }
    public static int size_ret()
    {
        return size;
    }
    
    static HashMap <Integer, String> relevant_docs=new HashMap<Integer, String>();
    
    
    public static void set_relevant_docs(String []Submit)
    {
        if(Submit.length!=0)
        {
            for (int i=0;i<Submit.length;i++)
            {
                //System.out.println(Submit[i]);
                relevant_docs.put(relevant_counter,Submit[i]);
                relevant_counter++;
            }
        }
        else {
            
        }
    }
    
    public static void clear_relevant_docs()
    {
        if(relevant_counter==0)
        { 
            relevant_docs.clear();
            System.err.println("done clearing");
        }
        else {
        ;
        }
    }
    
    public static HashMap<Integer, String> get_relevant_docs()
    {
        return relevant_docs;
    }
    
    public static void set_counter_to_zero()
    {
        relevant_counter=0;
        System.out.println("counter set to 0");
    }
    static String submit[];
    public static void set_submit(String[]Submit)
    {
        submit=Submit;
    }
    
    public static String[] ret_submit()
    {
        return submit;
    }
}
