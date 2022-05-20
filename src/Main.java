import java.util.*; // Scanner library
import java.io.*;  // PrintWriter and File library 

public class Main {
    public static void main(String[] args) throws Exception {        
        // check if args has two arguments
        if(args.length == 2) {
             // create a File object to contain the output file
             File outFile = new File(args[1]);
             // define a scanner to read input and PrintWriter to write on output file
             Scanner inFile = null;
             PrintWriter output = null;
             // create a LazyBST using the arguments in args[0]
             LazyBinarySearchTree tree = new LazyBinarySearchTree();
             String line = "";  // read file line by line
             try {
                 // initialize a file object containing input file from command line
                inFile = new Scanner(new File(args[0]));   
                // create output file
                outFile.createNewFile();
                // initialize PrintWriter output to contain outFile file
                output = new PrintWriter(outFile);

                // while a next line exists in inFile, read the lines an manipulate date
                while(inFile.hasNextLine()) {
                    line = inFile.nextLine().trim();
                    // create an string array to store data broken into parts
                    String[] inputLine = new String[line.length()];
                    // split data into two parts, using ":" as tokenizer
                    if(line.contains(":")) 
                        inputLine = line.split(":");
                    else   
                        inputLine[0] = line;
                    // will store error line, in case there's one
                    String error = inputLine[0];
                    // will determine which LazyBST function to execute based on inputLine[0]
                    // argument
                    switch(inputLine[0].toUpperCase()) {
                        // calls insert()
                        case "INSERT":
                            try {
                                boolean isInserted = tree.insert(Integer.parseInt(inputLine[1]));
                                output.println(isInserted);
                            } catch (IllegalArgumentException ex) {
                                output.println("Error in insert: " 
                                    + "IllegalArgumentException raised");
                            }
                        break;
                        // calls delete()
                        case "DELETE":
                            try {
                                boolean isDelete = tree.delete(Integer.parseInt((inputLine[1])));
                                output.println(isDelete);
                            } catch (IllegalArgumentException ex) {
                                output.println("Error in delete: " 
                                    + "IllegalArgumentException raised");
                            }                          
                        break;
                        // calls findMax()
                        case "FINDMAX":
                            int maxNum = tree.findMax();
                            output.println(maxNum);
                        break;
                        // calls findMin()
                        case "FINDMIN":
                            int minNum = tree.findMin();
                            output.println(minNum);
                        break;
                        // calls contains(x), given x
                        case "CONTAINS":
                            try {
                                boolean isContain = tree.contains(Integer.parseInt(inputLine[1]));
                                output.println(isContain);
                            } catch (Exception ex) {
                                output.println("Invalid input");
                            }
                        break;
                        // calls printTree()
                        case "PRINTTREE":
                            output.println(tree.toString());
                        break;
                        // calls height()
                        case "HEIGHT": 
                            output.println(tree.height());
                        break;
                        // calls size()
                        case "SIZE":
                            output.println(tree.size());
                        break;
                        // exits program if invalid input
                        case "":
                            break;
                        default:
                            output.println("Error in Line: " + error);
                    }
                }
                // close input and output files
                inFile.close();
                output.close();
             } catch(FileNotFoundException ex) {
                 System.err.println(inFile + "not found.");
             } catch(IOException ex) {
                 System.err.println("Unable to read " + inFile + " file.");
             }
        } else {
            System.out.println("Invalid arguments in command line.");
        }
    }
}