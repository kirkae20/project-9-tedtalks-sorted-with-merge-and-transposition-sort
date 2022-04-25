import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// edited original csv data file to create an extended version of my data
// original data: data.csv
// edited data: dataExtended.csv
public class Main {

    //Method declarations
    public static long transpositionSort(ArrayList<TedTalk> a, int size){
        boolean swapped = true;
        long numSwaps = 0;

        // iterate through list until list is sorted
        while (swapped) {
            swapped = false;
            // if list has an even number of elements
            // iterate through the even indexes comparing to the next element
            if (size % 2 == 0) {
                for(int i = 0; i < size - 1; i = i + 2) {
                    if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                        TedTalk temp = a.get(i);
                        a.set(i, a.get(i + 1));
                        a.set(i + 1, temp);
                        numSwaps++;
                        swapped = true;
                    }
                }
                // iterate through the odd indexes comparing to the next element, ignore last index with no pair
                for(int i = 1; i < size - 2; i = i + 2) {
                    if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                        TedTalk temp = a.get(i);
                        a.set(i, a.get(i + 1));
                        a.set(i + 1, temp);
                        numSwaps++;
                        swapped = true;
                    }
                }
            }
            // if list has an odd number of elements
            // iterate through the even indexes comparing to the next element, ignore last index with no pair
            else {
                for(int i = 0; i < size - 2; i = i + 2) {
                    if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                        TedTalk temp = a.get(i);
                        a.set(i, a.get(i + 1));
                        a.set(i + 1, temp);
                        numSwaps++;
                        swapped = true;
                    }
                }
                // iterate through the odd indexes comparing to the next element
                for(int i = 1; i < size - 1; i = i + 2) {
                    if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                        TedTalk temp = a.get(i);
                        a.set(i, a.get(i + 1));
                        a.set(i + 1, temp);
                        numSwaps++;
                        swapped = true;
                    }
                }
            }
        }
        return numSwaps;
    }
    public static ArrayList<Long> mergeSort(ArrayList<TedTalk> a, ArrayList<TedTalk> tmp, int left, int right,
                                               ArrayList<Long> swaps){
        // call mergeSort if left index is greater than right index
        if (left < right) {
            int middle = (left + right) / 2;
            // call mergeSort with left or right half of list
            mergeSort(a, tmp, left, middle, swaps);
            mergeSort(a, tmp, middle + 1, right, swaps);

            // call mergeSortedLists and have it return an ArrayList of long
            swaps = mergeSortedLists(a, tmp, left, middle, right, swaps);
        }
        return swaps;

    }
    public static ArrayList<Long> mergeSortedLists(ArrayList<TedTalk> a, ArrayList<TedTalk> tmp, int left,
                                                      int middle, int right, ArrayList<Long> swaps){
        int leftPosition = left;
        int rightPosition = middle + 1;
        long countSwaps = 0;

        // while the right position is not at the end of the right side and while the left position is not at
        // the end of the left side, compare the elements of the right and left positions
        while(right >= rightPosition && middle >= leftPosition) {
            // if the left position is less than the right position, add the left position to the temporary list
            // increase left position up one
            if(a.get(leftPosition).compareTo(a.get(rightPosition)) < 0) {
                tmp.add(a.get(leftPosition));
                leftPosition++;
            }
            // if the right position is less than the left position, add the right position to the temporary list
            // increase right position up one, increase countSwaps up one
            else {
                tmp.add(a.get(rightPosition));
                rightPosition++;
                countSwaps++;
            }
        }
        // while left position is not at end of left side, add the element at the left position to the list
        // increase the left position
        while (middle >= leftPosition) {
            tmp.add(a.get(leftPosition));
            leftPosition++;
        }
        // while right position is not at end of right side, add the element at the right position to the list
        // increase the right position
        while (right >= rightPosition) {
            tmp.add(a.get(rightPosition));
            rightPosition++;
        }


        // replace the sorted list back into the original arrayList
        for(int i = 0; i <= tmp.size() - 1; i++) {
            a.set(i + left, tmp.get(i));
        }
        int i = 0;
        // while the temporary ArrayList is still full, add items to the original ArrayList,
        // remove items from temporary ArrayList
        while(!tmp.isEmpty()) {
            a.set(left + i, tmp.get(0));
            tmp.remove(0);
            i++;
        }
        // add the number of swaps as a new element in the ArrayList of swaps
        swaps.add(countSwaps);
        return swaps;
    }

    public static ArrayList<TedTalk> createList (ArrayList<TedTalk> list, int size, String fileName)
            throws IOException {
        //Scanner scnr = new Scanner(System.in);
        // asks user for filename
        FileInputStream in = null;
        Scanner inputFile = null;

        // open FileInputStream
        // have FileInputStream run through Scanner
        in = new FileInputStream(fileName);
        inputFile = new Scanner(in);

        // ignores first line of file
        inputFile.nextLine();
        int counter = 0;

        while(inputFile.hasNext()) {
            String talk = inputFile.nextLine();
            String[] split = talk.split(",((?=[A-Z])|(?=[0-9])|(?=https))");

            if (split.length == 6 && counter <= size) {
                // assigns each String in the file line to object TedTalk
                // parses String integers into actual integers
                TedTalk info = new TedTalk(split[0], split[1], split[2], Integer.parseInt(split[3]),
                        Integer.parseInt(split[4]), split[5]);

                list.add(info);
                counter++;
            } else {
                continue;
            }
        }
        in.close();
        return list;
    }
    public static ArrayList<TedTalk> copyArrayList (ArrayList<TedTalk> list) {
        // copy list and return copy
        ArrayList <TedTalk> list2 = new ArrayList<TedTalk>();
        for(int i = 0; i < list.size(); i++) {
            list2.add(list.get(i));
        }
        return list2;
    }
    public static long totalSwaps(ArrayList<Long> countSwaps){
        // take in ArrayList of swaps, adds the swaps for each call of mergeSortedLists, returns total swaps
        int swaps = 0;
        for(int i = 0; i < countSwaps.size(); i++) {
            swaps += countSwaps.get(i);
        }
        return swaps;
    }
    public static void main(String [] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        File file = new File("sortedDataset.txt");
        PrintWriter out1 = new PrintWriter(file);
        File file2 = new File("numSwaps.txt");
        PrintWriter out2 = new PrintWriter(file2);

        System.out.println("Country list filename?: ");
        // asks user for filename
        String getFile = scnr.nextLine();

        ArrayList<TedTalk> list = new ArrayList<TedTalk>();
        ArrayList<TedTalk> list2 = new ArrayList<TedTalk>(); // copy of list
        ArrayList<TedTalk> tmp = new ArrayList<TedTalk>();   // temporary workspace
        ArrayList<Long> countSwaps = new ArrayList<>(); // ArrayList to count swaps in each call to mergeSortedLists

        list = createList(list, 1000, getFile);
        list2 = copyArrayList(list);

        out2.println("Size of dataset: 1,000");
        out2.println("Number of swaps: ");
        countSwaps = mergeSort(list, tmp, 0, 1000, countSwaps);
        long swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        out1.println("Sorted Dataset with MergeSort: \n");
        // print sorted information for size 1000
        for(TedTalk info: list) {
            out1.println(info.toString());
        }
        swaps = transpositionSort(list2, 1000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        out1.println("\nSorted Dataset with TranspositionSort: \n");
        // print sorted information for size 1000
        for(TedTalk info: list2) {
            out1.println(info.toString());
        }
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 10,000");
        out2.println("Number of swaps: ");
        list = createList(list, 10000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 10000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 10000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 20,000");
        out2.println("Number of swaps: ");
        list = createList(list, 20000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 20000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 20000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 30,000");
        out2.println("Number of swaps: ");
        list = createList(list, 30000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 30000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 30000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 40,000");
        out2.println("Number of swaps: ");
        list = createList(list, 40000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 40000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 40000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 50,000");
        out2.println("Number of swaps: ");
        list = createList(list, 50000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 50000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 50000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 60,000");
        out2.println("Number of swaps: ");
        list = createList(list, 60000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 60000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 60000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 70,000");
        out2.println("Number of swaps: ");
        list = createList(list, 70000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 70000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 70000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 80,000");
        out2.println("Number of swaps: ");
        list = createList(list, 80000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 80000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 80000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 90,000");
        out2.println("Number of swaps: ");
        list = createList(list, 90000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 90000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 90000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out2.println("Size of dataset: 100,000");
        out2.println("Number of swaps: ");
        list = createList(list, 100000, getFile);
        list2 = copyArrayList(list);
        countSwaps = mergeSort(list, tmp, 0, 100000, countSwaps);
        swaps = totalSwaps(countSwaps);
        out2.printf("%15s%d\n", "MergeSort: ", swaps);
        swaps = transpositionSort(list2, 100000);
        out2.printf("%23s%d\n\n", "TranspositionSort: ", swaps);
        list.clear();
        list2.clear();

        out1.close();
        out2.close();

    }
}
