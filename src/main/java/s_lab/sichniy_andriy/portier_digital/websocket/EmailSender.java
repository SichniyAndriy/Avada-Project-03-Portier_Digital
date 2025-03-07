package s_lab.sichniy_andriy.portier_digital.websocket;


public class EmailSender {

    private int counter = 0;

    public String sendEmail(String text) {
        String line = "Sending mail number " + ++counter;
        System.out.println(line);
        System.out.println(text);
        return line;
    }

    public String result() {
        return "Total emails sent: " + counter;
    }

}
