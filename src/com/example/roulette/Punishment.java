package com.example.roulette;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class Punishment {
    private ContentResolver contentResolver;
    private String[] interrestingContacts = {
            // Familie
            "mor",
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
            "kollega"
    };
    ArrayList<String> foundList = new ArrayList<String>();
    ArrayList<String> foundListNumbers = new ArrayList<String>();
    ArrayList<String> allContacts = new ArrayList<String>();
    ArrayList<String> allContactsNumbers = new ArrayList<String>();

    Punishment(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public void readContacts(){
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

        System.out.println(foundList);
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
}
