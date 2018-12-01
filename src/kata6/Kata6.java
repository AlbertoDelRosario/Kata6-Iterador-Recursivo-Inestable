package kata6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class Kata6 {

    public static void main(String[] args) throws FileNotFoundException {
        int count = 1;
        File file = new File("prueba");
        Iterator<File> iterator = iteratorOf(file.listFiles());                             //para los nombres
        //Iterator<Long> iterator = lengthsOf(iteratorOf(file.listFiles()));                //para los nombres y los tamaños en bytes
        //Iterator<Integer> iterator = kilobytes(lengthsOf(iteratorOf(file.listFiles())));  //para los nombres y los tamaños en kilobytes
        
        while (iterator.hasNext()){
            System.out.print(count++ + ":\t");
            System.out.println(iterator.next().getAbsolutePath());   //para los nombres
            //System.out.println("--> size: " + iterator.next() + "B");         //para los nombres y los tamaños en bytes
            //System.out.println("--> size: " + iterator.next() + "KB");        //para los nombres y los tamaños en kilobytes   
        }
    }
    
    private static Iterator<Integer> kilobytes(Iterator<Long> iterator) {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Integer next() {
                return (int) (iterator.next() / (1024));
            }
        };
    }

    private static Iterator<Long> lengthsOf(Iterator<File> iterator) {
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Long next() {
                File f = iterator.next();
                System.out.print(f.getAbsolutePath() + " ");
                return f.length();
            }
        }; 
    }

    private static Iterator<File> iteratorOf(File[] items){
        return new Iterator<File>() {
            private int index = 0;
            private Iterator<File> iter = null;
            @Override
            public boolean hasNext() {
                if (iter == null){
                    return (index < items.length);
                }else {
                    return items.length > 1;
                }
            }

            @Override
            public File next() {
                
                if(iter == null){
                    File f = (File) items[index++];
                    if (f.isFile()){
                        return f;
                    }else{
                        iter = iteratorOf(f.listFiles()); 
                        return f;
                    }
                }else{
                    if (iter.hasNext()){
                        File auxFile = iter.next();
                        if (!iter.hasNext()) iter = null;
                        return auxFile;
                    }else {
                        iter = null;
                        return this.next();
                    }
                }
            }
        };
    }    
}
