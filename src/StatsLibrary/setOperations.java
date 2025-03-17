package StatsLibrary;

import java.util.ArrayList; // need this to use arraylists since they are resizable lists

public class setOperations { // this class will have methods to do union, intersection, and complement

    // method to find the union of two lists (combines them without duplicates)
    public static ArrayList<String> findUnion(ArrayList<String> firstList, ArrayList<String> secondList) {
        ArrayList<String> unionResult = new ArrayList<>(firstList); // copy firstList into a new list

        for (String element : secondList) { // go through each item in secondList
            if (!unionResult.contains(element)) { // if it's not already in unionResult, add it
                unionResult.add(element);
            }
        }
        return unionResult; // return the final list with all unique elements
    }

    // method to find the intersection (common elements between both lists)
    public static ArrayList<String> findIntersection(ArrayList<String> firstList, ArrayList<String> secondList) {
        ArrayList<String> intersectionResult = new ArrayList<>(); // new empty list to store common items

        for (String element : firstList) { // go through firstList
            if (secondList.contains(element)) { // if secondList also has it, add to intersectionResult
                intersectionResult.add(element);
            }
        }
        return intersectionResult; // return the list of common elements
    }

    // method to find the complement (stuff in universalSet that isn't in subset)
    public static ArrayList<String> findComplement(ArrayList<String> universalSet, ArrayList<String> subset) {
        ArrayList<String> complementResult = new ArrayList<>(); // new list for missing items

        for (String element : universalSet) { // go through universal set
            if (!subset.contains(element)) { // if it's NOT in subset, add it to complement
                complementResult.add(element);
            }
        }
        return complementResult; // return the complement list
    }

    public static void main(String[] args) { // main method where the program actually runs

        // making a list of all the days of the week (this is the universal set)
        ArrayList<String> universalDaysOfWeek = new ArrayList<>();
        universalDaysOfWeek.add("Monday");
        universalDaysOfWeek.add("Tuesday");
        universalDaysOfWeek.add("Wednesday");
        universalDaysOfWeek.add("Thursday");
        universalDaysOfWeek.add("Friday");
        universalDaysOfWeek.add("Saturday");
        universalDaysOfWeek.add("Sunday");

        // making a list of work days (monday to friday)
        ArrayList<String> workDays = new ArrayList<>();
        workDays.add("Monday");
        workDays.add("Tuesday");
        workDays.add("Wednesday");
        workDays.add("Thursday");
        workDays.add("Friday");

        // making a list of weekend days (saturday and sunday)
        ArrayList<String> weekendDays = new ArrayList<>();
        weekendDays.add("Saturday");
        weekendDays.add("Sunday");

        // testing the methods

        // union should give all 7 days since work + weekend covers everything
        System.out.println("Union of workDays and weekendDays: " + findUnion(workDays, weekendDays));

        // intersection should be empty because workDays and weekendDays have no common elements
        System.out.println("Intersection of workDays and weekendDays: " + findIntersection(workDays, weekendDays));

        // complement should return saturday and sunday because those aren't in workDays
        System.out.println("Complement of workDays in universalDaysOfWeek: " + findComplement(universalDaysOfWeek, workDays));
    }
}