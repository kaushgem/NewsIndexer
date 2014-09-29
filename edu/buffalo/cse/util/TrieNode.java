package edu.buffalo.cse.util;

// Reference - 

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TrieNode implements Comparable<TrieNode> 
{
    private char m_char;
    public Integer m_word_count;
    private TrieNode m_parent = null;
    private HashMap<Character, TrieNode> m_children = null;

    public TrieNode(TrieNode parent, char c)
    {
        m_char = c;
        m_word_count = 0;
        m_parent = parent;
        m_children = new HashMap<Character, TrieNode>();            
    }

    public void AddWord(String word, int index)
    {
        if (index < word.length())
        {
            char key = word.charAt(index);
            if (Character.isLetter(key)) // should do that during parsing but we're just playing here! right?
            {
                if (!m_children.containsKey(key))
                {
                	
                    m_children.put(key, new TrieNode(this, key)) ;
                }
                m_children.get(key).AddWord(word, index + 1);
            }
            else
            {
                // not a letter! retry with next char
                AddWord(word, index + 1);
            }
        }
        else
        {
            if (m_parent != null) // empty words should never be counted
            {
                  m_word_count++;                        
               
            }
        }
    }

    public int GetCount(String word, int index)
    {
        if (index < word.length())
        {
            char key = word.charAt(index);
            if (!m_children.containsKey(key))
            {
                return -1;
            }
            return m_children.get(key).GetCount(word, index + 1);
        }
        else
        {
            return m_word_count;
        }
    }

    public void GetTopCounts( List<TrieNode> most_counted,  int distinct_word_count,  int total_word_count)
    {
        if (m_word_count > 0)
        {
            distinct_word_count++;
            total_word_count += m_word_count;
        }
        if (m_word_count > most_counted.get(0).m_word_count)
        {
            most_counted.set(0,this) ;
            
           Collections.sort(most_counted); 
        }
        for (Character key : m_children.keySet())
        {
            m_children.get(key).GetTopCounts( most_counted,  distinct_word_count,  total_word_count);
        }
    }

    

	

	@Override
	public int compareTo(TrieNode other) {
		// TODO Auto-generated method stub
		int last = this.m_word_count.compareTo(other.m_word_count);
        return last == 0 ? this.m_word_count.compareTo(other.m_word_count) : last;
 
		
	}
	
	@Override
	 public  String toString()
     {
         if (m_parent == null) return "";
         else return m_parent.toString() + m_char;
     }

	/*@Override
	public int compare(TrieNode arg0, TrieNode arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
*/
	
	

	
}


