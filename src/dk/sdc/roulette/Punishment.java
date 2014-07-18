package dk.sdc.roulette;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Locale;

public class Punishment {

    private ContentResolver contentResolver;
    private String[] interrestingContacts = {
            // Familie
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
    private String[] interrestingContactsGerman = {
            // Familie
            "mutter",
            "mum",
            "mom",
            "pap",      //papa, paps, papi
            "vater",
            "dad",
            "pappi",
            "sis",
            "bro",
            "haus",     // zuhause usw.

            // Freundin?
            "schatz",
            "bab",      // baby, babe
            "hübsch",
            "freund",
            "hot",
            "skat",
            "sü",
            "<3",
            "honey",
            "sexy",
            "darling",
            "ass",
            "arsch",
            "ex",
            "geliebt",
            "♥",
            "❤",
            "♡",

            // Kumpels
            "bitch",
            "bff",

            // Arbeit
            "chef",
            "boss",
            "vorgesetzter",
            "lehrer",
            "arbeit",
            "kollege"
    };

    private String[] evilMessages = {
            // Max string length:
          //"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            "Shiiit. Jeg har lige taget graviditetstesten.. DEN ER POSITIV :O!!! Ring til mig!",
            "Tak for igår ;) jeg har sku stadig svært ved at gå.. Håber vi kan gøre det igen :D",
            "Fuck jeg er liderlig, er du hjemme?",
            "... jeg har noget vigtigt at sige til dig! Ring til mig.",
            "Jeg vil kneppe dig i røven indtil du græder ;)",
            "Er det dig der har lagt en lort på mit værelse??",
            "RING TIL MIG!!",
            "Kan du være sammen imorgen ;)?",
            "Der er en elg i min have.",
            "Jeg har klamydia..",
            "Jeg regner med at være der om 2 minutter",
            "Stripperen kommer ikke alligevel :-/",
            "Er du single?",
            "Daniel aflyste.. Kan vi holde festen hos dig? Jeg har vodkaen..",
            "Hvor meget koster dine ydelser?",
            "Tak for blomsterne :)",
            "Kommer du til min fødselsdag?",
            "Vil du komme sammen :)<3?",
            "Mit røvhul brænder, jeg kan dårligt nok gå :O!?",
            "Jeg elsker dig<3",
            "Fik du købt en strap-on?",
            "Jeg fik lavet tatoveringen af satan som du foreslog :-D",
            "Glemte jeg lighteren hos dig?",
            "Var det pornhub du snakkede om?",
            "Har du mine underbukser?",
            "Græder du også efter sex?",
            "Har du et kondom jeg kan låne?"
    };

    private String[] evilMessagesGerman = {
            // Max string length:
          //"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            "Shiiit. Ich habs gerade gecheckt. Ich bin SCHWANGER!!! Ruf mich an!",
            "Thx, für gestern ;) Es tut immernoch weĥ^^ Hoffe wir können das wiederholen? :D",
            "Fuck bin ich gerade geil, bist du zuhause?",
            "... ich muss dir was wichtiges sagen! Ruf mich an.",
            "Ich will dich knallen bist du heuelst. ;)",
            "Hast du in mein Zimmer geschissen??",
            "RUF MICH AN!!",
            "morgen rumhängen ;)?",
            "Da steht ein Elch in meinem Garten.",
            "Ich habe Klamydia..",
            "bin in 2min bei dir",
            "Der Stripper kommt leider doch nicht. :(",
            "Bist du single?",
            "Daniel hat abgesagt. Können wir bei dir feiern? Ich habe den Vodka mit..",
            "Wie viel bezahle ich bei dir?",
            "Danke für die Blumen :)",
            "Kommst du zu meinem Geburtstag?",
            "Willst du mit mir zusammen sein? <3 :)",
            "Mein arschloch brennt wie Feuer, ich kann kaum laufen! :O ?",
            "Ich liebe dich! <3",
            "Hast du den Strap-On gekauft?",
            "Ich habe mir Satan tätovieren lassen, so wie du es vorgeschlagen hast. :D",
            "Liegt mein Feuerzeug noch bei dir?",
            "Du meintest doch pornhub oder?",
            "Hast du meine Unterhose?",
            "Weinst du auch nach dem Sex?",
            "Kan ich ein Kondom von dir Leihen?"
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
        String[] searchIn = Locale.getDefault().getLanguage().equals("de") ? interrestingContactsGerman : interrestingContacts;

        for(String interrestingContact : searchIn){
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
        if( foundList.size() > 3 ){
            // Use interresting contact.
            int i = (int)(Math.random() * foundList.size());
            selectedName = foundList.get(i);
            selectedhoneNumber = foundListNumbers.get(i);
        } else {
            try {

                // Use random contact.
                int i = (int) (Math.random() * allContacts.size());
                selectedName = allContacts.get(i);
                selectedhoneNumber = allContactsNumbers.get(i);
            } catch (Exception e) {

                // L'easy, default..
                selectedName = "L'Easy";
                selectedhoneNumber = "+4588888888";
            }
        }
        if( Locale.getDefault().getLanguage().equals("de")) {
            selectedMessage = evilMessagesGerman[(int) (Math.random() * evilMessagesGerman.length)];
        } else {
            selectedMessage = evilMessages[(int) (Math.random() * evilMessages.length)];
        }
    }
}
