import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class pc {
    private float possibility; /*The arrival possibility of a packet */
    private Queue<Integer> buffer= new LinkedList<>();
    private int wavelength; /*The wavelength used to communicate with the server*/

    public pc(float possibility, int wavelength){
        this.possibility=possibility;
        this.wavelength=wavelength;
    }

    public int getWavelength() {
        return wavelength;
    }

    public boolean Arrival(){
        Random random=new Random();
        int randomInt= random.nextInt(100)+1;
        if((randomInt/100)<=possibility){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean RegisterPacket(int timeSlot){
        if(buffer.size()==5){
            return false;
        }
        else if (buffer.size()>5) {
            /*ΘΥΜΙΣΟΥ ΝΑ ΤΟ ΒΓΑΛΕΙΣ ΤΟ PRINT
            *
            *
            *
            *
            *
            *
            *
            * */
            System.out.println("MORE THAN 5 ELEMENTS ON BUFFER QUEUE");
            return false;
        }
        else {
            buffer.add(timeSlot);
            return true;
        }
    }
}
