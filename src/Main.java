import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        /*-----------VALUE INITIALISATION-----------*/
        int totalDelay=0;
        Random random=new Random();
        float TransmissionPossibility=0.5f;
        int slot=1, CreatedPackets=0,LostPackets=0,PacketsSent=0;
        ArrayList<Integer> RegisteredTransmission=new ArrayList<>();//The registered for transmission clients' ids are stored
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
            clients.add(new pc(possibility,(i%4+1),i+1));
        }
        /*-----------END OF VALUE INITIALISING-----------*/
        /*-----------TIME SLOT OPERATIONS-----------*/
        while (slot<100){
            for(int i=0;i<8;i++){
                if(clients.get(i).WantToTransmit()){//Checking if the specific client has a packet in its buffer
                    if ((random.nextInt(100)+1)/100<=TransmissionPossibility){//if so the client tries to transmit with 'TransmissionPossibility' possibility
                        if(transmission.get(clients.get(i).getWavelength()-1)){//if another client using the same wavelength already wants to transmit the collision flag is set to true
                            collision.set(clients.get(i).getWavelength()-1,true);
                        }
                        else{//if no other client using the same wavelength wants to transmit the transmission flag for the equivalent wavelength is set to true and the client's id is registered for transmission
                            transmission.set(clients.get(i).getWavelength()-1,true);
                            RegisteredTransmission.add(clients.get(i).getId());
                        }
                    }
                }
            }
            for (int i=0;i<8;i++){
                if(RegisteredTransmission.contains(clients.get(i).getId())){
                    if(!collision.get(clients.get(i).getWavelength()-1)){
                        totalDelay+=slot-clients.get(i).transmit();
                        PacketsSent+=1;
                    }
                }
            }
            for (int i=0;i<RegisteredTransmission.size();i++){//Empty the transmission list for the next slot
                RegisteredTransmission.remove(0);
            }
            for (int i=0;i<4;i++){//re-initialising transmission and collision flags for each wavelength
                transmission.set(i,false);
                collision.set(i,false);
            }
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
        /*-----------END OF TIME SLOT OPERATIONS-----------*/
    }
}