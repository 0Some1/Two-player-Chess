import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Server {
    public static ServerSocket listener;
    public static ArrayList<Socket> sockets=new ArrayList<>();


    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server() throws IOException {
        listener=new ServerSocket(9999);
        System.out.println("server started !");

        while (true){
            new Pair(listener.accept()).start();
        }

    }



    class Pair extends Thread {

        public Pair(Socket socket){
            sockets.add(socket);
        }
        @Override
        public void run() {
            if (sockets.size()==2){
                try {
                    Random random=new Random();
                    int rand=random.nextInt(1);
                    if (rand==0){
                        new Handler(sockets.get(0),sockets.get(1)).start();
                    }else {
                        new Handler(sockets.get(1),sockets.get(0)).start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                sockets.removeAll(sockets);
            }
        }
    }
    class Handler extends Thread {
        private Socket socketW;
        private Socket socketB;
        private Scanner inW;
        private PrintWriter outW;
        private ObjectOutputStream objectOutputW;
        private ObjectInputStream objectInputW;
        private Scanner inB;
        private PrintWriter outB;
        private ObjectOutputStream objectOutputB;
        private ObjectInputStream objectInputB;
        boolean isEnd=false;

        public Handler(Socket socketW,Socket socketB) throws IOException {
            this.socketW = socketW;
            this.socketB= socketB;
            inW=new Scanner(socketW.getInputStream());
            inB=new Scanner(socketB.getInputStream());
            outW=new PrintWriter(socketW.getOutputStream(),true);
            outB=new PrintWriter(socketB.getOutputStream(),true);


        }

        @Override
        public void run() {
            //Welcome massage
            outW.println("Hi You are White player and you two successfully are connected !!");
            outB.println("Hi You are Black player and you two successfully are connected !!");
            String input;
            while (!isEnd){

                if (inW.hasNextLine()){
                    input=inW.nextLine();
                    if (input.contains("end")||input.contains("time")){
                        outB.println(input);
                        isEnd=true;
                        continue;
                    }else {
                        outB.println(input);
                    }
                }

                if (inB.hasNextLine()){
                    input=inB.nextLine();
                    if (input.contains("end")||input.contains("time")){
                        outW.println(input);
                        isEnd=true;
                        continue;
                    }else {
                        outW.println(input);
                    }
                }





            }




        }



    }

}