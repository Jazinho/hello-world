import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Double> czytajLinie(String line){

        String number="";
        double numberDouble;
        List<Double> wsp = new ArrayList<>();
        char c;

        for (int i = 0; i < line.length(); i++){

            c = line.charAt(i);

            if(c == ' '){
                if(i!=0) {
                    numberDouble = Double.valueOf(number);
                    wsp.add(numberDouble);
                }
                number = "";
            }else{
                number+=c;
            }

        }
        //konwersja ostatniego współczynnik w linii
        //numberDouble = Double.valueOf(number);
        //wsp.add(numberDouble);
        if(number.equals(">")){
            wsp.add(1.0);
        }else{
            wsp.add(0.0);
        }

        return wsp;
    }

    public static void main(String[] args){

        double[] tab0=null , tab1=null , tab2=null , tab3=null, tab4=null ;
        boolean toMax = true, bigger1=true, bigger2=true, bigger3=true;

        try(BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {

            String line = br.readLine();

            if(line.equals("p")) { //---------------PIERWOTNY

                line = br.readLine();
                List<Double> wsp = czytajLinie(line);
                tab0 = new double[]{wsp.get(0), wsp.get(1)};

                line = br.readLine();
                wsp = czytajLinie(line);
                tab1 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                if(wsp.get(3)==0.0)bigger1=false;

                line = br.readLine();
                wsp = czytajLinie(line);
                tab2 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                if(wsp.get(3)==0.0)bigger2=false;

                line = br.readLine();
                wsp = czytajLinie(line);
                tab3 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                if(wsp.get(3)==0.0)bigger3=false;

                line = br.readLine();
                if (line.equals("min")) toMax = false;

            }else if(line.equals("d")){ //--------------------DUALNY

                line = br.readLine();
                List<Double> wsp = czytajLinie(line);
                tab0 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};

                line = br.readLine();
                wsp = czytajLinie(line);
                tab1 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2), wsp.get(3), wsp.get(4)};
                if(wsp.get(4)==0.0)bigger1=false;


                line = br.readLine();
                wsp = czytajLinie(line);
                tab2 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2), wsp.get(3), wsp.get(4)};
                if(wsp.get(4)==0.0)bigger2=false;

                line = br.readLine();
                if(line.equals("min")) toMax=false;

            }else if(line.equals("i")){//-----------------------------------ILORAZOWY

                line = br.readLine();
                List<Double> wsp = czytajLinie(line);
                if(wsp.get(2)==null){
                    tab0 = new double[]{wsp.get(0), wsp.get(1)};
                }else{
                    tab0 = new double[]{wsp.get(0), wsp.get(1),wsp.get(2)};
                }

                line = br.readLine();
                wsp = czytajLinie(line);
                if(wsp.get(2)==null){
                    tab4 = new double[]{wsp.get(0), wsp.get(1)};
                }else{
                    tab4 = new double[]{wsp.get(0), wsp.get(1),wsp.get(2)};
                }

                line = br.readLine();
                wsp = czytajLinie(line);
                tab1 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                //if(wsp.get(3)==0.0)bigger1=false;

                line = br.readLine();
                wsp = czytajLinie(line);
                tab2 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                //if(wsp.get(3)==0.0)bigger2=false;

                line = br.readLine();
                wsp = czytajLinie(line);
                tab3 = new double[]{wsp.get(0), wsp.get(1), wsp.get(2)};
                //if(wsp.get(3)==0.0)bigger3=false;

                line = br.readLine();
                if (line.equals("min")) toMax = false;

            }else{
                System.out.println("Nie określono rodzaju problemu");
                return;
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(tab0 == null || tab1 == null || tab2 == null)return;

        Ograniczenie ogr1 = new Ograniczenie(tab1,bigger1);
        Ograniczenie ogr2 = new Ograniczenie(tab2,bigger2);
        Ograniczenie ogr3 = new Ograniczenie(tab3,bigger3);

        FunkcjaCelu f1 = new FunkcjaCelu(tab0,toMax);
        if(tab4!=null){
            FunkcjaCelu f2 = new FunkcjaCelu(tab4,!toMax);
            Ilorazowy.rozwiaz(f1,f2,ogr1,ogr2,ogr3);
            return;
        }

        if(f1.wsp.length==2) {
            Pierwotny.rozwiaz(f1, ogr1, ogr2, ogr3);
        }else if(f1.wsp.length==3){
            Dualny.rozwiaz(f1, ogr1, ogr2);
        }else{
            System.out.println("Nie potrafie rozwiazac");
        }

    }
}
