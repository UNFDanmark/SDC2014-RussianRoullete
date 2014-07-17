package com.example.roulette;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class Punishment {

    private ContentResolver contentResolver;
    private String[] interrestingContacts = {
            // Familie
            "janu"/*,
            "momse",
            "mutti",
            "far",
            "dad",
            "pappi",
            "søs",
            "bro",
            "hjem",

            // Kæreste?
            "kære",
            "bab", // babe, baby
            "smukke",
            "hotte",
            "skat",
            "søde",
            "<3",
            "honey",
            "sexy",
            "darling",
            "asshole",
            "ex",
            "eks",
            "elskede",
            "♥",
            "❤",
            "♡",

            // Venner, veninder
            "bitch",
            "so",
            "bff",

            // Arbejde
            "chef",
            "boss",
            "leder",
            "lærer",
            "arbejde",
            "kollega"*/
    };
    private String[] evilMessages = {
          //"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            "Shiiit. Jeg har lige taget graviditetstesten.. DEN ER POSITIV :O!!! Ring til mig!",
            "Tak for igår ;) jeg har sku stadig svært ved at gå.. Håber vi kan gøre det igen :D",
            "Fuck jeg er liderlig, er du hjemme?",
            "... jeg har noget vigtigt at sige til dig! Ring til mig.",
            "Jeg vil kneppe dig i røven indtil du græder ;)",
            "Er det dig der har lagt en lort på mit værelse??",
            "RING TIL MIG!!",
            "Kan du være sammen imorgen ;)?"
    };
    private ArrayList<String> foundList = new ArrayList<String>();
    private ArrayList<String> foundListNumbers = new ArrayList<String>();
    private ArrayList<String> allContacts = new ArrayList<String>();
    private ArrayList<String> allContactsNumbers = new ArrayList<String>();
    private Context ctx;
    public String selectedName;
    public String selectedhoneNumber;
    public String selectedMessage;

    Punishment(ContentResolver contentResolver, Context ctx){
        this.contentResolver = contentResolver;
        this.ctx = ctx;
    }

    public void sendSMS()
    {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(selectedhoneNumber, null, selectedMessage, null, null);
        } catch (Exception e){
            for(int i = 0; i < 4; i++)
                Misc.message(ctx, "Error sending SMS..");
        }
    }

    private void readContacts(){
        ContentResolver cr = contentResolver;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        parseContact(name, phoneNo);
                    }
                    pCur.close();
                }
            }
        }
    }

    private void parseContact(String name, String number){
        for(String interrestingContact : interrestingContacts){

            if(name.toLowerCase().contains(interrestingContact) && !foundList.contains(name)){
                foundList.add(name);
                foundListNumbers.add(number);
                break;
            }
        }

        allContacts.add(name);
        allContactsNumbers.add(number);
    }

    public void getContactAndMessage(){
        readContacts();
        if( foundList.size() > 0 ){
            // Use interresting contact.
            int i = (int)(Math.random() * foundList.size());
            selectedName = foundList.get(i);
            selectedhoneNumber = foundListNumbers.get(i);
        } else {
            // Use random contact.
            int i = (int)(Math.random() * allContacts.size());
            selectedName = allContacts.get(i);
            selectedhoneNumber = allContactsNumbers.get(i);
        }
        selectedMessage = evilMessages[(int)(Math.random() * evilMessages.length)];
    }
}
