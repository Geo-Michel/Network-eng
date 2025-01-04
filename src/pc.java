import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class pc {
    private double possibility; /*The arrival possibility of a packet */

    private int id;
    private Queue<Integer> buffer= new LinkedList<>();
    private int wavelength; /*The wavelength used to communicate with the server*/

    public pc(double possibility, int wavelength, int id){
        this.possibility=possibility;
        this.wavelength=wavelength;
        this.id=id;
    }

    public int getWavelength() {
        return wavelength;
    }

    public int getId(){return id;}

    /*A method that returns true if a new packet has arrived given the arrival possibility of a packet.*/
    public boolean Arrival(){
        Random random=new Random();
        float randomFloat= random.nextFloat();
        if(randomFloat<=possibility){
            return true;
        }
        else{
            return false;
        }
    }

    /*A method that returns true if the packet that has arrived is registered in the buffer. If the buffer is full it returns false and the packet is lost.*/
    public boolean RegisterPacket(int timeSlot){
        if(buffer.size()==5){
            System.out.println("pc id:"+id+"    Buffer is full aborting packet");
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
            System.out.println("---------------------------------------MORE THAN 5 ELEMENTS ON BUFFER QUEUE-------------------------");
            return false;
        }
        else {
            buffer.add(timeSlot);
            return true;
        }


    }
    public boolean WantToTransmit(){
        return !buffer.isEmpty();
    }

    public int transmit(){
        return buffer.remove();
    }


}
