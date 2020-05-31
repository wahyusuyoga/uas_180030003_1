package com.AA183.wahyusuyoga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 3;
    private final static String DATABASE_NAME = "db_filmku";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_SUTRADARA = "Sutradara";
    private final static String KEY_SINOPSIS_FILM = "Sinopsis_Film";
    private final static String KEY_GENRE= "Genre";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM= "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_GAMBAR + " TEXT, "
                + KEY_TGL + " DATE, " + KEY_SUTRADARA + " TEXT, "
                + KEY_SINOPSIS_FILM + " TEXT, " + KEY_GENRE + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinopsisFilm());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinopsisFilm());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinopsisFilm());
        cv.put(KEY_GENRE, dataFilm.getGenre());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(3));
                } catch (ParseException er) {
                    er.printStackTrace();
                }
                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        tempDate,
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;
        Date tempDate = new Date();

        // Menambah data film ke-1
        try{
            tempDate = sdFormat.parse("06/03/2020 14:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }


        Film film1 = new Film(
                idFilm,
                "Joker",
                storeImageFile(R.drawable.gambarfilm1),
                tempDate,
                " Todd Phillips",
                "Pada 1981, Arthur Fleck, seorang badut yang berusia 40 tahun tinggal bersama ibunya, Penny di Kota Gotham yang kacau balau. Dia menderita kelainan otak yang menyebabkan dia tertawa pada waktu yang tidak tepat dan dia sering mengunjungi pekerja layanan sosial untuk mendapatkan obatnya. Setelah sekelompok anak jalanan mencuri papan milik Arthur dan mengeroyokinya di lorong, salah seorang rekan kerjanya meminjamkan pistol sebagai alat perlindungan diri. Suatu saat, penyakitnya kambuh ketika ia sedang menaiki kereta api, sehingga Sophie keheranan dengan tingkahnya dan Arthur memberikan sebuah kartu untuk menjawab keheranannya. Arthur kemudian menjalin hubungan baik dengannya, yang kemudian baru disadarinya bahwa Sophie tinggal di apartemen yang sama dengannya.\n" +
                        "\n" +
                        "Selama kunjungan ke rumah sakit anak-anak, pistol Arthur terjatuh dari sakunya, sehingga ia dipecat dari pekerjaannya. Arthur juga mengetahui bahwa program layanan sosial ditutup, sehingga dia tidak memiliki cara untuk mendapatkan obat. Dalam perjalanan pulang menggunakan kereta bawah tanah, Arthur dirundung oleh tiga pebisnis muda Wall Street, sehingga ia menembak mati ketiganya dengan pistol yang ia pinjam itu. Arthur tidak menyadari pembunuhan itu akan memulai gerakan unjuk rasa terhadap orang kaya di kota itu dengan menggunakan topeng badut. Beberapa hari sebelumnya, Thomas Wayne mencalonkan dirinya sebagai wali kota karena merasa resah dengan kekacauan di kota itu yang tidak kunjung berhenti.\n" +
                        "\n" +
                        "Sophie menghadiri acara lawakan tunggal Arthur yang berlangsung sangat buruk. Arthur tertawa tak terkendali dan kesulitan menyampaikan kelakarnya. Seorang pembawa acara gelar wicara populer, Murray Franklin, menayangkan video itu secara langsung sebagai ejekan. Arthur mencuri surat yang ibunya tulis kepada Thomas Wayne yang merupakan salah satu orang terpandang di kota itu dan menemukan dirinya adalah putra tidak sah Wayne. Dia memaki-maki ibunya karena merahasiakan ini darinya dan tak lama setelah itu, Penny jatuh sakit sehingga dirawat di rumah sakit. Arthur juga dilecehkan oleh dua detektif yang curiga dengan keterlibatannya dalam penembakan kereta bawah tanah, tetapi ia menyangkalnya.\n" +
                        "\n" +
                        "Keadaan kota menjadi semakin kacau dengan pengunjuk rasa yang tumpah ruah di mana-mana. Ketika para pengunjuk rasa mulai berkelahi dengan petugas keamanan, Arthur menyelinap ke sebuah gedung tempat sebuah acara khusus untuk tokoh ternama dihelat. Arthur berjumpa dengan Thomas Wayne dan mempertanyakan status dirinya dengan Thomas. Thomas mengatakan bahwa Penny gila dan bahkan bukan ibu kandung Arthur, sembari menampar Arthur setelahnya. Arthur mengunjungi Rumah Sakit Arkham untuk mencari tahu akan kebenaran perkataan Thomas. Arthur mencuri berkas kasus Penny dan menemukan bahwa dia memang diadopsi setelah ditinggalkan ketika bayi. Dia juga mengetahui bahwa Penny berlaku kasar kepadanya ketika dia masih kecil, termasuk trauma kepala yang serius yang mengakibatkan tawa patologisnya. Arthur kembali ke rumah sakit dan langsung menutup kepala ibunya dengan bantal hingga tewas kehabisan udara. Dia kembali ke gedung apartemennya dan memasuki kamar Sophie. Sophie kaget dengan kehadirannya dan memintanya pergi. Arthur kemudian menyadari bahwa pengalamannya dengan Sophie hanyalah ilusi.\n" +
                        "\n" +
                        "Seorang pegawai dari acara Murray Franklin menelepon dan meminta Arthur untuk tampil di acara itu. Arthur setuju dan berencana bunuh diri di acara itu. Saat ia bersolek dan mengenakan pakaiannya, ia yang memegang gunting kecil dikunjungi oleh dua rekan kerja lama yang ingin memberikan belasungkawa atas kematian ibunya. Arthur malah menusuk leher dan mata salah seorang di antaranya dengan gunting itu, lalu membenturkan kepalanya berkali-kali hingga tewas saat itu juga. Arthur membiarkan salah satu yang lain tetap hidup karena kebaikan kepadanya pada masa lalu. Dalam perjalanan ke studio, ia dikejar oleh dua detektif ke sebuah kereta yang penuh dengan pengunjuk rasa badut. Salah satu detektif secara tidak sengaja menembak mati seorang pengunjuk rasa, sehingga pengunjuk rasa lain mulai mengeroyoki kedua detektif itu hingga kritis, dan Arthur melarikan diri dari kegaduhan itu.\n" +
                        "\n" +
                        "Sebelum acara itu dimulai, Arthur meminta Murray memperkenalkannya sebagai Joker, sebuah olok-olok Murray beberapa waktu yang lalu terhadapnya. Acara itu berlangsung dengan lancar seperti biasanya, tetapi Arthur malah terus-menerus mengakui pembunuhan di kereta bawah tanah itu dan mempertanyakan kemunafikan masyarakat yang menyanjung ketiga pemuda itu sembari merendahkannya, dengan menyebut masyarakat lebih memilih menginjak mayatnya di jalan raya alih-alih menguburkannya secara layak. Murray berusaha menenangkan suasana, tetapi Arthur tidak menggubrisnya. Arthur langsung menembak mati Murray tepat di kepalanya saat itu juga, sehingga banyak penonton yang lari ketakutan dan ia ditangkap polisi karenanya. Dalam perjalanan ke kantor polisi, Arthur melihat Gotham sedang dirundung kekacauan oleh pengunjuk rasa. Satu di antara pengunjuk rasa mengejar keluarga Wayne hingga ke sebuah lorong dan menembak mati Thomas serta Martha, sehingga Bruce hanya bisa terpaku dengan keadaan itu sembari menangisi kepergian orang tuanya. Mobil yang ditumpangi Arthur ditabrak ambulans yang dikemudikan sejumlah pengunjuk rasa, sehingga kedua polisi di mobil itu tewas, sementara Arthur selamat dengan sejumlah luka di tubuhnya. Mereka langsung menyelamatkan Arthur saat itu juga dan membaringkannya di sebuah mobil. Arthur tersadar dan bangkit dari siumannya, sehingga pengunjuk rasa merayakannya dengan penuh gegap gempita, yang ditanggapi dengan tarian Arthur.\n" +
                        "\n" +
                        "Beberapa waktu kemudian, Arthur diinterogasi oleh seorang pekerja sosial di Rumah Sakit Arkham dan tertawa terpingkal-pingkal. Ketika ditanya, Arthur hanya mengatakan bahwa mereka tidak akan mengerti. Arthur angkat kaki dari tempat itu dengan meninggalkan jejak berdarah dari sepatunya.",
                "psikologis"
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke-2
        try{
            tempDate = sdFormat.parse("24/12/2018 22:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Jumanji",
                storeImageFile(R.drawable.gambarfilm2),
                tempDate,
                "Jake Kasdan",
                "Dalam Jumanji: The Next Level, keempat sahabat kembali lagi, tetapi permainannya telah berubah. Saat memutuskan masuk lagi ke Jumanji untuk menyelamatkan salah satu dari mereka, mereka menemukan segalanya tak seperti yang diduga. Para pemain harus melaju ke wilayah-wilayah tersembunyi dan belum dijamah, dari gurun nan gersang ke pegunungan bersalju, demi meloloskan diri dari permainan paling berbahaya di dunia",
                "petualangan"
        );

        tambahFilm(film2, db);
        idFilm++;

        // Data Film ke-3
        try{
            tempDate = sdFormat.parse("29/12/2018 20:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "sie doel the moovie",
                storeImageFile(R.drawable.gambarfilm3),
                tempDate,
                "\tH.Rano Karno",
                "Empat belas tahun sudah Sarah (Cornelia Agatha) pergi tanpa kabar dan perpisahan yang jelas, menyimpan kerinduan dalam hati Doel (H. Rano Karno) yang sudah berumah tangga dengan Zaenab (Maudy Koesnaedi). Melalui Hans (Adam Jagwani), Sarah meminta Doel dibawa ke Belanda. untuk mempertemukannya dengan Dul (Rey Bong), anak hasil pernikahannya dengan Doel. Maka, dengan dalih urusan bisnis, Hans mengundang Doel datang ke Belanda.\n" +
                        "Tanpa mengetahui maksud sebenarnya, Doel ditemani Mandra (H Mandra YS) terbang ke Amsterdam dan membuat pecah kerinduan yang dipendamnya selama ini. Doel bertemu dengan Sarah dan anaknya Dul. Tapi, kini Doel berada didalam pilihan yang sulit. Harapan, Kerinduan, Keresahan dan keihkhlasan menyelimuti kisah tiga insan yang kerap dipermainkan oleh takdir. Dan pilihan untuk berada dalam jalan terbaik berada di tangan Doel yang menggalau tanpa mau membohongi dan melukai perasaan siapa pun.\n",
                "Drama, Romance,"
        );

        tambahFilm(film3, db);
        idFilm++;

        // Data Film ke-4
        try{
            tempDate = sdFormat.parse("30/06/2016 19:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "The Dictator ",
                storeImageFile(R.drawable.gambarfilm4),
                tempDate,
                "Larry Charles",
                "Selama bertahun-tahun, Republik Wadiya (terletak di Tanduk Afrika, menggantikan bangsa sebenarnya Eritrea) dipimpin oleh Laksamana Jenderal Haffaz Aladeen (Sacha Baron Cohen), seorang yang kekanak-kanakan, tirani, seksis, anti-Barat, dan antisemitis yang mengelilingi dirinya dengan pengawal perempuan, mensponsori al-Qaeda dan bekerja pada pengembangan senjata nuklir untuk menyerang Israel. Ia seorang yang sangat khas dengan jenggot lebatnya. Aladeen juga menolak untuk menjual ladang minyak negeri Wadiya demi melaksanakan pesan yang dibuat oleh ayahnya di ranjang kematiannya. Setelah Dewan Keamanan PBB memutuskan untuk campur tangan secara militer, Aladeen pun memutuskan ke Markas Besar PBB di New York.\n" +
                        "Tak lama setelah tiba, Aladeen diculik oleh Clayton (John C. Reilly), pembunuh bayaran yang sebenarnya disewa oleh pamannya, Tamir (Ben Kingsley). Tamir kemudian mengganti Aladeen dengan seorang yang sangat mirip dengannya yang bernama Efawadh. Tamir bermaksud untuk memanipulasi penandatanganan dokumen nominal demokratisasi Wadiya agar dapat membuka ladang minyak negara itu untuk China dan lainnya untuk kepentingan asing. Aladeen berhasil lolos dari Clayton yang malah membunuh dirinya sendiri. Namun, sebelumnya Clayton berhasil mencukur habis jenggot Aladeen. Dengan demikian, tak ada orang yang tahu siapa Aladeen sebenarnya. Melihat berita kebakaran, Tamir berpikir Aladeen telah tewas.\n" +
                        "Mengembara di New York, Aladeen pertemuan Zoey (Anna Faris), seorang aktivis hak asasi manusia yang menawarkan dia pekerjaan padanya. Aladeen menolak tawaran dan tanpa sengaja bertemu dengan Nadal (Jason Mantzoukas), mantan kepala program senjata nuklir Wadiya. Dulu semasa masih di Wadiya, Aladeen pernah memerintahkan eksekusi mati untuk Nadal karena menolak permintaan Aladeen. Aladeen mengikutinya ke sebuah kedai bernama “Little Wadiya” yang dihuni oleh pengungsi dari negaranya—yang sebetulnya orang yang pernah dieksekusi.\n" +
                        "Karena perbuatan bodohnya, Aladeen pun dengan mudah dikenali orang-orang di Little Wadiya tersebut. Ia hampir dibunuh oleh mereka sampai akhirnya diselamatkan oleh Nadal. Nadal memberitahu Aladeen bahwa semua orang yang pernah dieksekusi Aladeen berada di Little Wadiya dan tidak dibunuh, karena sang algojo sangat anti padanya.\n" +
                        "Aladeen pun meminta Nadal untuk membantunya meraih kembali posisinya sebagai pemimpin Wadiya. Nadal setuju untuk membantu Aladeen menggagalkan rencana Tamir dan mendapatkan kembali posisinya sebagai diktator ‘sah’, dengan syarat bahwa Aladeen membuatnya ketua program nuklir Wadiya lagi.\n" +
                        "Aladeen pun akhirnya mau menerima tawaran pekerjaan Zoey, agar ia mendapatkan akses untuk ke hotel tempat rombongan dari Wadiya menginap. Aladeen yang mengaku bernama Alison Burgers ini menjadi lebih dekat dengan Zoey setelah dia menolak ajakan seksual dan mengajarkan kepadanya bagaimana masturbasi, dan akhirnya malah jatuh cinta. Demi Zoey, Aladeen mulai mengatur ulang toko milik Zoey menjadi terkoordinasi dengan baik. Zoey pun senang dengan kinerja Aladeen.\n" +
                        "Namun, hubungan Aladeen dengan Zoey menjadi buruk setelah ia menyatakan cinta dan mengatakan bahwa ia sebenarnya adalah Aladeen sang diktator. Zoey pun menolak cinta Aladeen karena tidak bisa mencintai seorang pria yang begitu brutal untuk rakyatnya sendiri.\n" +
                        "Aladeen patah hati dan ingin dibunuh diri sebelum akhirnya berhasil diselamatkan Nadal lagi dengan mengatakan bahwa ia adalah diktator terhebat yang pernah ada.\n" +
                        "Setelah mendapatkan jenggot palsu, Aladeen pergi ke hotel dan meminta Efawadh—yang sebenarnya idiot—untuk segera pergi. Pada upacara penandatanganan, ia mengatakan bahwa Tamir seorang penipu di depan delegasi PBB. Penandatangan dokumen perubahan sistem demokrasi di Wadiya pun batal. Aladeen malah menlanjutkan dengan pidato berapi-api memuji kebajikan dari kediktatoran. Namun, setelah melihat Zoey di dalam ruangan, ia menyatakan cintanya dan mengetahui Zoey sangat berpandangan, bersumpah untuk demokrasi bagi negaranya dan membuka ladang minyak Wadiya untuk bisnis. Marah dengan Aladeen, Tamir berusaha untuk membunuh dia tetapi Efawadh melompat untuk melindungi Aladeen yang membut Efawadh tertembak.\n" +
                        "Setahun kemudian, Wadiya melaksanakan pemilu demokratis untuk pertama kalinya, meskipun warga dipaksa mendukung Aladeen. Setelah itu, ia menikahi Zoey, tetapi terkejut ketika dia menghancurkan kaca dan mengungkapkan Zoey adalah seorang Yahudi. Padahal sepanjang film Aladeen ditampilkan bersumpah untuk “menghancurkan Israel”. Adegan selama kredit menunjukkan konvoi Aladeen ini, sekarang terdiri dari mobil ramah lingkungan, mengunjungi Nadal kembali. Kemudian Zoey mengungkapkan dalam sebuah wawancara televisi bahwa dia hamil tengah anak pertama. Aladeen menanggapi kabar itu dengan menanyakan apakah Zoey ingin memiliki anak laki-laki atau aborsi.[4]\n",
                "comedy,dark comedy"
        );

        tambahFilm(film4, db);
        idFilm++;

        // Data Film ke-5
        try{
            tempDate = sdFormat.parse("17/02/2012 19:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film5 = new Film(
                idFilm,
                "zoombie land",
                storeImageFile(R.drawable.gambarfilm5),
                tempDate,
                "\tRuben Fleischer",
                "Zombieland merupakan sebuah film Amerika Serikat yang dirilis pada tahun 2009. Film yang disutradarai oleh Ruben Fleischer ini pemainnya antara lain ialah Jesse Eisenberg, Woody Harrelson, Emma Stone dan Abigail Breslin. Film ini dirilis pada tanggal 2 Oktober 2009.\n" +
                        "Seluruh Dunia terkena penyakit sapi gila yang menyebabkan berubahnya para manusia menjadi zombie. Seorang murid yang belum terinfeksi, Columbus Ohio (Jesse Eisenberg) berusaha melarikan diri dari kota zombie dengan menetapkan banyak peraturan yang dibuatnya sendiri untuk bertahan, seperti latihan kardio, pukulan ganda, larangan untuk menjadi pahlawan, waspada kamar mandi dan pemasangan sabuk pengaman di mobil.\n" +
                        "Hari berikutnya, Columbus bertemu orang yang belum ter-infeksi, Tallahase(Woody Harrelson) yang menjadi pemburu zombie dadakan sejak para zombie berkuasa di bumi. Tallahase dan Columbus Berhenti ke Swalayan untuk mencari sebuah makanan bernama Twinkie. Tallahase dan Columbus bertemu Wichita (Emma Stone) dan Little Rock (Abigail Breslin), 2 gadis ini malah menipu kedua orang tersebut dengan melucuti senjata milik keduanya. Tallahase dan Columbus menemukan Mobil Truck Besar dan penuh dengan senjata besar. tetapi, 2 gadis ini tetap mencuri Truck Tallahase. saat Tallahase mengambil senjata Little Rock, Columbus mencoba menghentikan Perdebatan Wichita. saat Tallahase berdamai dengan Little Rock dan Wichita, Columbus menanyakan kotanya sendiri. saat Wichita memeberi informasi tentang kotanya telah hancur, Columbus memutar otak untuk tinggal bersama mereka.\n" +
                        "Hari berikutnya, Tallahase mengajak Columbus dan lainya ke rumah Bill Murray. Columbus mengajak Wichita ke bioskop Murray lalu saat Tallahase, Wichita, dan Murray mengerjai Columbus, Columbus malah menyesal menembak Murray. Keesokan harinya, Wichita dan Little Rock Pergi menningalkan Columbus dan Tallahase. Columbus mulai strees karena kehilangan Wichita yg pergi ke Taman Hiburan bersama Little Rock.\n" +
                        "Wichita dan Little Rock dalam bahaya ketika para zombie datang. Tallhase malah berubah pikiran untuk pergi ke Meksiko untuk menemukan Twinkie. Columbus setuju untuk menolong Wichita dan Little Rock. Saat Columbus datang, Columbus sangat ketakutan melawan Zombie badut sehingga ia terpaksa mengubah peraturannya menjadi \"Jadilah Pahlawan\". Columbus berhasil menyelamatkan Wichita dan Little Rock, tetapi Tallahase menyalahkan Twinkie tidak ada di Taman Hiburan Snack. lalu saat Wichita pura-pura meningalkan Columbus dan Tallhase, Little Rock memberi Tallhase sebuah Twinkie, lalu Columbus sadar dan akhirnya menemukan keluarga barunya.\n",
                "comedy/horor"
        );

        tambahFilm(film5, db);
        idFilm++;

        // Data Film ke-5
        try{
            tempDate = sdFormat.parse("17/02/2012 19:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film6 = new Film(
                idFilm,
                "Dead pool",
                storeImageFile(R.drawable.gambarfilm6),
                tempDate,
                "Tim Miller\n" +
                        "\n",
                "Deadpool adalah sebuah film pahlawan super Amerika yang berdasarkan pada karakter Marvel Comics dengan nama yang sama. Film tersebut merupakan instalmen kedelapan dalam serial film X-Men. Film tersebut disutradarai oleh Tim Miller, dengan sebuah permainan latar buatan Rhett Reese dan Paul Wernick, dan dibintangi oleh Ryan Reynolds, Morena Baccarin, Ed Skrein, T. J. Miller, Gina Carano, Brianna Hildebrand, Andre Tricoteux, dan Leslie Uggams.\n" +
                        "Pengembangan dimulai pada bulan Februari 2004 dengan New Line Cinema, namun pindah pada bulan Maret 2005 sampai 20th Century Fox yang membeli hak film tersebut. Pada bulan Mei 2009, setelah Reynolds memerankan karakter dalam X-Men Origins: Wolverine, dengan kekecewaan umum para penggemar, Fox meminjamkan film tersebut kepada para penulis, dan Miller dipekerjakan untuk debut sutradaranya pada bulan April 2011. Antusias untuk rekaman rekaman CGI yang bocor Oleh Miller pada bulan Juli 2014 menyebabkan Fox menyinari film tersebut pada bulan September. Pengecoran tambahan dimulai pada awal 2015, dan pengambilan gambar utama dimulai di Vancouver dari bulan Maret sampai Mei.\n" +
                        "Deadpool ditayangkan perdana di Paris pada tanggal 8 Februari 2016, dan dirilis pada 12 Februari di Amerika Serikat dalam format IMAX, DLP, D-Box, dan format premium. Kritikus memuji penampilan Reynolds dan juga gaya filmnya, penggambaran karakter tituler yang setia, dan urutan tindakan, namun mengkritik plotnya sebagai rumusan. Ia menerima berbagai penghargaan dan nominasi, termasuk dua nominasi Golden Globe Award untuk Best Motion Picture - Musical or Comedy dan Best Actor - Motion Picture Musical or Comedy, dan nominasi Producers Guild of America Award untuk Best Theatrical Motion Picture. Ini juga memenangkan dua Critics' Choice Movie Awards untuk Best Comedy dan Best Actor in a Comedy.\n" +
                        "Film ini juga sukses komersial yang signifikan, meraup lebih dari $783 juta di seluruh dunia dan memecahkan banyak catatan box office, menjadi film ke sembilan terlaris tahun 2016, film rating-R terlaris sepanjang masa ketika tidak disesuaikan dengan inflasi, dan Film X-Men terlaris. Tak lama setelah kesuksesan film tersebut, Fox memerintahkan pengembangan sekuel yang rencananya akan dirilis pada 1 Juni 2018.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n",
                " Action/Comedy"
        );

        tambahFilm(film6, db);
    }
}

