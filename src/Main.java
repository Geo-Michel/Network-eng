import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        float TransmissionPossibility=0.5f;
        int slot=1, CreatedPackets=0,LostPackets=0;
        ArrayList<Boolean> transmission=new ArrayList<>();/*A boolean for each wavelength to check if a client using the wavelength index+1 (eg if a client using W1 wants to transmit the Boolean object with index 0 becomes true)*/
        for (int i=0;i<4;i++){
            transmission.add(false);
        }
        ArrayList<Boolean> collision=new ArrayList<>();/*A boolean for each wavelength to check if more than one client using the wavelength index+1 want to transmit on the same time slot(eg if 2 or more clients using W1 want to transmit on the same time slot the Boolean object with index 0 becomes true)*/
        for (int i=0;i<4;i++){
            collision.add(false);
        }
        Scanner input=new Scanner(System.in);
        System.out.println("Type the arrival possibility of packets in float format (eg 10%-->0,1)");
        float possibility=input.nextFloat();/*The arrival possibility of a packet for all clients*/
        while(possibility<0 ||possibility>1){
            System.out.println("Invalid input type the arrival possibility of packets in float format from 0-1 (eg 10%-->0,1)");
            possibility= input.nextFloat();
        }
        ArrayList<pc> clients=new ArrayList<>();
        for (int i=0;i<8;i++){
            clients.add(new pc(possibility,(i%4+1)));
        }
        while (slot<100){
            /*ΠΡΩΤΑ ΠΡΕΠΕΙ ΝΑ ΥΛΟΠΟΙΗΣΩ ΤΟ TRANSMIT ΚΑΙ ΜΕΤΑ ΓΙΝΕΤΑΙ ΤΟ ARRIVAL*/
            for (int i=0;i<8;i++){
                if(clients.get(i).Arrival()){
                    CreatedPackets+=1;
                    if(!clients.get(i).RegisterPacket(slot)){
                        LostPackets+=1;
                    }
                }
            }
            slot+=1;
        }

    }
}