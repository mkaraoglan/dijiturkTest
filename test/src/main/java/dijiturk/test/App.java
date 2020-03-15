package dijiturk.test;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	System.out.println( "s World!" );
    	Dijiturk base = new Dijiturk();
    	base.initializeDriver();
    }
}
