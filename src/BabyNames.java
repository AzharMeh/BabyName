import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.Scanner;

public class BabyNames {
    public void BabyBirth(){
        FileResource fr = new FileResource();
        for(CSVRecord rec : fr.getCSVParser(false)){
            String name = rec.get(0);
            System.out.println("Name : "+ name+
                ":    Gender: "+rec.get(1)+
                ":   No. Birth: "+ rec.get(2));
        }
    }
    public int totalBirth(FileResource fr){
        int girlsRank = 0;
        int boysRank =0;
        int totalRank = 0;
        int totalBirth = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            totalBirth += Integer.parseInt(rec.get(2));
            totalRank += 1;
            if(rec.get(1).equalsIgnoreCase("M")){
                totalBoys +=Integer.parseInt(rec.get(2));
                boysRank += 1;
            }
            else{
                totalGirls +=Integer.parseInt(rec.get(2));
                girlsRank += 1;
            }
        }
        System.out.println("Tortal Birth "+totalBirth+" total Rank: "+totalRank);
        System.out.println("Tortal Boys "+totalBoys+" total boys Rank: "+boysRank);
        System.out.println("Tortal Girls "+totalGirls+" total girls Rank: "+girlsRank);
        return totalBirth;
    }

    @SuppressWarnings("resource")
    public int getRank(String year, String name, String gender){
        int rank = 0;
        
        FileResource fr = new FileResource("D:/JAVA BLUE J/CSV Project/Baby names/us_babynames_by_year/yob"+year+".csv");
        for(CSVRecord rec : fr.getCSVParser(false)){

            if(rec.get(1).equalsIgnoreCase(gender)){
                String currName = rec.get(0);
                rank += 1;
                if(currName.equalsIgnoreCase(name)){
                    return rank;
                }   }
        }
        return -1;
    }

    public String getName(String year, int rank, String gender){
        int currRank = 0;
        
        FileResource fr = new FileResource("src/Data/us_babynames_by_year/yob"+year+".csv");
        for(CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equalsIgnoreCase(gender)){
                String name = rec.get(0);
                currRank += 1;
                if(currRank == rank){
                    return name;
                }
            }
        }
        return "Not Found";
    }
    public void whatIsNameInYear(String name, String year, 
    String newYear, String gender){
        
        int currRank = getRank(year, name, gender); 
        String newName = getName(newYear,currRank, gender);
        System.out.println(name+" born in "+year+" would be "+newName+
            " if she was born in "+newYear+".");
    }
    // this method finds your top rank in selected files
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int highRank = 0;
        File fname = null;
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int currRank = 0;
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(rec.get(1).equalsIgnoreCase(gender)){
                    String currName = rec.get(0);
                    currRank += 1;
                }
                if(rec.get(0).equalsIgnoreCase(name)){
                    if(highRank == 0){                        
                        highRank = currRank;}
                    if(highRank > currRank){
                    highRank = currRank;
                    fname = f;
                    }    
                }
                }            
            }
            if (highRank == 0){return -1;}
        
        System.out.println("Your top rank is in year of file : "+fname.getName());
        return highRank;
    } 

    public double getAverageRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        double rank = 0;
        
        int count = 0;
            for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int currRank = 0;
            for(CSVRecord rec : fr.getCSVParser(false)){                
                if(rec.get(1).equals(gender)){
                String currName = rec.get(0);                
                currRank += 1;
                if(currName.equals(name)){
                rank += currRank;
                count += 1;
                }
            }            
            }            
        }
        if (count == 0){return -1;}

        return rank/count;   
    } 
    public int getTotalBirthsRankedHigher(String name, String gender){
        int rank = 0;
        int birth = 0;       
        FileResource fr = new FileResource();
        for(CSVRecord rec : fr.getCSVParser(false)){
        
        if(rec.get(1).equals(gender)){
        rank += 1;        
        birth += Integer.parseInt(rec.get(2));
        if(rec.get(0).equals(name)){
        System.out.println("Name "+name+" is ranked : "+
        rank+" in year : ");
        return birth -Integer.parseInt(rec.get(2));
        }        
        }
        }
        return -1;
    }
// Some Test Methods to check the functioning
    public void testTotalBirth(){
        Scanner input = new Scanner(System.in);        
        System.out.println("Input a year to find total Birth in that year\nInput in between 1880-2014:  ");
        String year = input.nextLine();
        FileResource fr = new FileResource("src/Data/us_babynames_by_year/yob"+year+".csv");        
        int totalBirth = totalBirth(fr);
        System.out.println("Total birth on that year is "+totalBirth);
    }
    public void testGetRank(){
        Scanner yearInput = new Scanner(System.in);        
        System.out.println("Input a year to Get Your Rank in that year according to popularity of your name \nInput in between 1880-2014:  ");
        String year = yearInput.nextLine();

        Scanner nameInput = new Scanner(System.in);        
        System.out.println("Input Your Name :  ");
        String name = nameInput.nextLine();// To test Type name from Data Given

        Scanner genderInput = new Scanner(System.in);        
        System.out.println("Your Gender type (M) for male and (F) for female :  ");
        String gender = genderInput.nextLine();

        int rank = getRank(year, name, gender);
        if(rank == -1){ 
            System.out.println("This name is not in Data which is gatherd by USA authorities");
        }
        System.out.println("Your Rank is"+rank);
    } 
    public void testGetName(){
        Scanner yearInput = new Scanner(System.in);        
        System.out.println("Input a year to check Rank \nInput in between 1880-2014:  ");
        String year = yearInput.nextLine();

        Scanner rankInput = new Scanner(System.in);        
        System.out.println("Input Rank :  ");
        int rank = rankInput.nextInt();

        Scanner genderInput = new Scanner(System.in);        
        System.out.println("Your Gender type (M) for male and (F) for female :  ");
        String gender = genderInput.nextLine();
               
        String name = getName(year, rank, gender);
        if(name == ""){ 
            System.out.println("This name is not in Data which is gatherd by USA authorities");
        }
        System.out.println(name+"'s Rank is "+rank+" in year "+year);
    } 



    public static void main(String[] args){
        BabyNames data = new BabyNames();        
        data.testGetName();
    }
}
