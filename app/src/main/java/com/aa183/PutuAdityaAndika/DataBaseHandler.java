package com.aa183.PutuAdityaAndika;

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

public class DataBaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME ="db_BUKUNOVELADITYAAJA";
    private final static String TABLE_BERITA = "t_berita";
    private final static String KEY_ID_BERITA ="ID_Berita";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_ISI_BERITA = "Isi_Berita";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.getDefault());
    private Context context;

    public DataBaseHandler(Context ctx){
        super (ctx, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = ctx;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = " CREATE TABLE " + TABLE_BERITA
                + "(" + KEY_ID_BERITA + " INTERGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_ISI_BERITA + " TEXT, "
                + KEY_LINK + " TEXT);";


        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiBeritaAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BERITA;
        db.execSQL(DROP_TABLE);
        onCreate(db);


    }

    public void tambahBerita(Berita dataBerita){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_BERITA, dataBerita.getIdBerita());
        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());
        db.insert(TABLE_BERITA, null, cv);
        db.close();
    }
    public void tambahBerita(Berita dataBerita,SQLiteDatabase db){
        ContentValues cv = new ContentValues();


        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());
        db.insert(TABLE_BERITA, null, cv);
    }

    public void editBerita(Berita dataBerita) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());

        db.update(TABLE_BERITA, cv, KEY_ID_BERITA + "=?", new String[]{String.valueOf(dataBerita.getIdBerita())});
        db.close();


    }

    public void hapusData(int idBerita) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BERITA, KEY_ID_BERITA + "=?", new String[]{String.valueOf(idBerita)});
        db.close();
    }

    public ArrayList<Berita> getAllBerita() {
        ArrayList<Berita> dataBerita = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BERITA;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr. moveToFirst()){
            do {
                Date tempDate = new Date();
                try{
                    tempDate = sdFormat.parse(csr.getString(2));
                }catch(ParseException er) {
                    er.printStackTrace();
                }

                Berita tempBerita = new Berita(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataBerita.add(tempBerita);
            }while (csr.moveToNext());
        }

        return dataBerita;
    }

    private  String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBeritaAwal(SQLiteDatabase db){
        int idBerita = 0;
        Date tempDate = new Date();

        //Menambahkan data berita ke -1
        try{
            tempDate = sdFormat.parse("30/05/2020 06:22");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Berita berita1 = new Berita(
                idBerita,
                "Dilan 1990",
                tempDate,
                storeImageFile(R.drawable.berita1),
                "Dilan1990",
                "Pidi Baiq",
                "Novel ini menceritakan sebuah kisah seorang perempuan yang bernama Milea. Milea merupakan seorang murid yang baru saja pindah dari Jakarta. Ketika Milea pergi menuju sekolah barunya di Bandung, dia dihampiri dengan teman laki-laki satu sekolahan dengannya. temannya itu adalah seorang yang suka meramal.\n" +
                        "Laki-laki yang suka meramal itu berkata bahwa nanti mereka berdua akan bertemu besok di hari minggu. Pada awalnya Melia acuh dengan lelaku itu, tapi dia merasa terganggu karena setiap hari laki-laki itu selalu saja menghampirinya dan membuat dia senang ketika bertemu denganya. Milea mencari tahu laki-laki itu. Ternyata laki-laki itu bernama Dilan.\n" +
                        "Suatu hari, Dilan ingin menemani Milea pulang naik angkot,Dilan berbisik, ” Milea, kamu cantik, tapi aku belum mencintaimu. enggak tahu sore. Tunggu saja”. Kata yang diucapkan Dilan membuat jantungnya berdetak dengan kencang, Milea kaget dengan apa yang diucapkan Dilan. Dengan diam Milea mendengar ucapan Dilan, saat itu juga Melia teringat dengan pacarnya bernama Beni yang tinggal di Jakarta.\n" +
                        "Dilan mendekati Milea memakai cara yang unik,romantis, dan tak biasa, karena hal itu Milea terus memikirkannya. Dilan sering meberikan hadiah kepada Melia seperti cokelat melalui POS, membawa seorang tukang pijat saat Milea sedang sakit, dan saat Milea ulang tahun Dilan memberi kado sebuah TTS ( teka teki silang ), yang lucunya TTS itu ada tulisan “Selamat Hari Lahir Melia, Ini aku persembahkan hadiah untuk kamu, Hanya sebuah TTS, tapi semuanya sudah aku isi, aku cinta kamu, aku tidak ingin kamu jadi pusing karena mengisi TTS ini”.\n" +
                        "Seiring berjalanya waktu, Dilan dan Milea pun semakin akrab. Milea tahu tentang Dilan beberaa hal dari temannya yang bernama Wati, sepupu Dilan sekelas dengan Milea. Sekolah Milea mendapatkan kesempatan untuk mengikuti acara cerdas cermat yang diselenggarakan oleh TVRI, para siswa yang tidak mengikuti lomba boleh untuk memberikan semangat kepada teman-temannya yang mengikti lomba dengan ikut ke Jakarta.\n" +
                        "Milea pun ikut, dia sudah memiliki rencana untuk bertemu pacarnya Beni di Jakarta. Milea lama menunggu Beni yang janji akan bertemu di TVRI, akan tetapi Beni tidak datang-datang. Pada akhirnya, Milea pergi unutk makan bersama temannya Wati dan Wanda. Ketika itu Beni datang dengan penuh emosi dan marah melihat Milea makan dengan Nandan, teman sekelasnya. Hubungan mereka kandas ketika itu juga. Jelang beberapa hari Beni mengajak Milea untuk kembali menjadi pacarnya, tapi Milea menolak penawaran Beni.\n" +
                        "Akhirnya Milea Bertemu dengan ibuna Dilan. Ternyata, ibunya Dilan asiknya sama seperti Dilan. Ibunya Dilan pun senang dengan Milea, jadi ibunya Dilan memberikan dukungannya penuh agar mereka jadian.\n" +
                        "Akhirnya mereka berdua pun resmi pacaran. Sampai-sampai, Dilan membuat surat resmi dan di sah kan menggunakan materai yang isinya seperti teks proklamasi hari jadianya Milea dan Dilan",
                "http://aliefviakusuma.blogspot.com/2017/08/sinopsis-novel-dilan-dia-adalah-dilanku.html"

        );

        tambahBerita(berita1, db);
        idBerita++;

        try{
            tempDate = sdFormat.parse("30/05/2020 09:22");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Berita berita2 = new Berita(
                idBerita,
                "Laskar Pelangi",
                tempDate,
                storeImageFile(R.drawable.berita2),
                "Laskar Pelangi",
                "Andrea Hirata",
                "Novel Laskar Pelangi bercerita tentang kehidupan anak-anak di Belitong. Laskar pelangi yang ditulis oleh Andrea Hirata dimulai dengan kisah miris dunia pendidikan di Indonesia dimana sebuah sekolah yang kekurangan murid hendak ditutup.\n" +
                        "\n" +
                        "\n" +
                        " \n" +
                        "Sekolah tersebut adalah SD Muhammadiyah di Gentong Belitung Timur. tetapi, karena murid yang terdaftar genap berjumlah 10, sekolah dengan bangunan seadanya tersebut tetap diijinkan beraktifitas seperti biasanya. Sepuluh murid tersebut kemudian disebut laskar pelangi. Nama yang diberikan guru mereka bernama Bu Mus.\n" +
                        "\n" +
                        "Pemberian Nama Laskar Pelangi disebabkan karena mereka sangat senang terhadap pelangi yang terlihat sangat indah dari negeri belitong. Novel berjudul Laskar Pelangi ini adalah novel pertama dari tetralogi Andrea Hirata.\n" +
                        "\n" +
                        "Andrea Hirata kemudian melanjutkan ceritanya berturut-turut yaitu Sang Pemimpi, Endesor. serta Maryamah Karpov.\n" +
                        "\n" +
                        "Laskar Pelangi sudah menjadi buku sastra terlaris sepanjang sejarah perbukuan di Indonesia. Dan perkembangan terakhirnya, novel ini sudah diterbitkan di berbagai benua dalam berbagai bahasa.\n" +
                        "\n" +
                        "Tokoh dalam novel ini adalah Ikal, Lintang, Sahara, Mahar, A Kiong, Syahdan, Kucai, Borek, Trapani, dan juga Harun. Mereka adalah sahabat yang kisahnya memesona dunia lewat tangan dingin sang penulis. Buku laskar pelangi bercerita keseharian mereka di sekolah dan di lingkungan sosial. Mereka adalah anak-anak desa dengan tekad luar biasa. Perjalanan mereka dipenuhi kejadian yang tak terduga. Secara perlahan mereka menemukan keunggulan ddalam diri dan persahabatan. Ini mungkin yang menjadi titik fokus Andrea Hirata. Ia juga piawai menyisip komedi dalam kisah ini.\n" +
                        "\n" +
                        "Sudut pandang bercerita dalam novel ini menggunakan orang pertama yakni “aku”. Aku sendiri adalah si Ikal. Ia anak yang pandai meski berada di urutan kedua setelah Lintang, bocah terpandai di dalam kelas mereka. Si Ikal ini menaruh minat yang besar pada sastra. Hal ini terlihat dari kegemarannya menulis puisi. Lain lagi dengan tokoh Lintang. Ia digambarkan sebagai anak yang sangat jenius.\n" +
                        "\n" +
                        "Orangtuanya seorang nelayan, yang miskin dan hanya tidak memiliki perahu. Mereka memiliki keluarga dalam jumlah yang melimpah, 14 kepala. Lintang sangat suka matematika. Namun, cita-citanya menjadi seorang ahli matematika harus terpangkas dengan tuntutan membantu orangtua menafkahi keluarga. Terlebih saat ayahnya meninggal.\n" +
                        "\n" +
                        "\n" +
                        " \n" +
                        "Tokoh lainnya adalah Sahara. Ia merupakan anak perempuan satu-satunya dalam cerita ini. Ia berpendirian kuat dan cenderung keras kepala. Sementara itu, Mahar, ia digambarkan bertubuh ceking dan mencintai seni. Ia suka menyanyi dan gemar pada okultisme. Tokoh berikutnya adalah A kiong. Dari namanya sangat jelas kalau ia merupakan keturunan Tionghoa. Ia sangat menyukai Mahar dan mengikutinya kemanapun. Ia digambarkan tak rupawan tetapi hatinya “tampan”.\n" +
                        "\n" +
                        "Lanjut ke Syahdan. Perangainya ceria meski ia tak pernah menonjol dalam kelas. Sementara itu Kucai, adalah tokoh dalam cerita yang didaulat menjadi ketua kelas. Ia digambarkan menderita penyakit rabun jauh sebab ia kekurangan gizi. Borek, Trapani dan Harun adalah anggota laskar` pelangi yang terakhir. Borek digambarkan sebagai anak yang terobsesi dengan otot.",

                    "http://caranovela.blogspot.com/2011/12/sinoposis-laskar-pelangi-singkat-padat.html"
        );
        tambahBerita(berita2, db);
        idBerita++;

        try{
            tempDate = sdFormat.parse("30/05/2020 10:22");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Berita buku3= new Berita(
                idBerita,
                "Sang Pemimpi",
                tempDate,
                storeImageFile(R.drawable.buku3),
                "Sang Pemimpi",
                "Andrea Hirata",
                "Novel ini adalah novel kedua dari tetralogi Laskar pelangi karya Andrea Hirata. Sang Pemimpi adalah sebuah kisah kehidupan yang mempesona yang akan membuat pembacanya percaya akan tenaga cinta, percaya pada kekuatan mimpi dan pengorbanan, selin itu juga memperkuat kepercayaan kepada Tuhan. Andrea berkelana menerobos sudut-sudut pemikiran di mana pembaca akan menemukan pandangan yang berbeda tentang nasib, tantangan intelektualitas, dan kegembiraan yang meluap-luap, sekaligus kesedihan yang mengharu biru. Selayaknya kenakalan remaja biasa, tetapi kemudian tanpa disadari kisah dan karakter-karakter dalam buku ini lambat laun menguasai, potret-potret kecil yang menawan akan menghentakkan pembaca pada rasa humor yang halus namun memiliki efek filosofis yang meresonansi.\n" +
                        "\n" +
                        "Tiga orang pemimpi. Setelah tamat SMP, melanjutkan ke SMA Bukan Main, di sinilah perjuangan dan mimpi ketiga pemberani ini dimulai. Ikal salah satu dari anggota Laskar Pelangi dan Arai yang merupakan saudara sepupu Ikal yang sudah yatim piatu sejak SD dan tinggal di rumah Ikal, sudah dianggap seperti anak sendiri oleh Ayah dan Ibu Ikal, dan Jimbron, anak angkat seorang pendeta karena yatim piatu juga sejak kecil. Namun, pendeta yang sangat baik dan tidak memaksakan keyakinan Jimbron, malah mengantarkan Jimbron menjadi muslim yang taat.\n" +
                        "\n" +
                        "Arai dan Ikal begitu pintar di sekolahnya, sedangkan Jimbron, si penggemar kuda ini biasa-biasa saja. Malah menduduki rangking 78 dari 160 siswa. Sedangkan Ikal dan Arai selalu menjadi lima dan tiga besar. Mimpi mereka sangat tinggi, karena bagi Arai, orang susah seperti mereka tidak akan berguna tanpa mimpi-mimpi. Mereka berdua mempunyai mimpi yang tinggi yaitu melanjutkan belajar ke Sorbonne Perancis. Mereka terpukau dengan cerita Pak Balia, kepala sekolahnya, yang selalu meyebut-nyebut indahnya kota itu. Kerja keras menjadi kuli ngambat mulai pukul dua pagi sampai jam tujuh dan dilanjutkan dengan sekolah, itulah perjuangan ketiga pemuda itu. Mati-matian menabung demi mewujudkan impiannya. Meskipun kalau dilogika, tabungan mereka tidak akan cukup untuk sampi ke sana. Tapi jiwa optimisme Arai tak terbantahkan.\n" +
                        "\n" +
                        "Selesai SMA, Arai dan Ikal merantau ke Jawa, Bogor tepatnya. Sedangkan Jimbron lebih memilih untuk menjadi pekerja ternak kuda di Belitong. Jimbron menghadiahkan kedua celengan kudanya yang berisi tabungannya selama ini kepada Ikal dan Arai. Dia yakin kalau Arai dan Ikal sampai di Perancis, maka jiwa Jimbron pun akan selalu bersama mereka. Berbula-bulan terkatung-katung di Bogor, mencari pekerjaan untuk bertahan hidup susahnya minta ampun. Akhirnya setelah banyak pekerjaan tidak bersahabat ditempuh, Ikal diterima menjadi tukang sortir (tukang Pos), dan Arai memutuskan untuk merantau ke Kalimantan. Tahun berikutnya, Ikal memutuskan untuk kuliah di Ekonomi UI. Dan setelah lulus, ada lowongan untuk mendapatkan biasiswa S2 ke Eropa. Beribu-ribu pesaing berhasil ia singkirkan dan akhrinya sampailah pada pertandingan untuk memperebutkan 15 besar.\n" +
                        "\n" +
                        "Saat wawancara tiba, tidak disangka, profesor pengujinya begitu terpukau dengan proposal riset yang diajukan Ikal, meskipun hanya berlatar belakang sarjana Ekonomi yang masih bekerja sebagai tukang sortir, tulisannya begitu hebat. Akhirnya setelah wawancara selesai, siapa yang menyangka, kejutan yang luar biasa. Arai pun ikut dalam wawancara itu. Bertahun-tahun tanpa kabar berita, akhirnya mereka berdua dipertemukan dalam suatu forum yang begitu indah dan terhormat. Begitulah Arai, selalu penuh dengan kejutan. Semua ini sudah direncanaknnya bertahun-tahun. Ternyata dia kuliah di Universitas Mulawarman dan mengambil jurusan Biologi. Tidak kalah dengan Ikal, proposal risetnya juga begitu luar biasa dan berbakat untuk menghasilkan teori baru.\n" +
                        "\n" +
                        "Akhirnya sampai juga mereka pulang kampung ke Belitong. Ketika ada surat datang, mereka berdebar-debar membuka isinya. Pengumuman penerima Beasiswa ke Eropa. Arai begitu sedih karena dia sangat merindukan kedua orang tuanya. Arai sangat ingin membuka kabar itu bersama orang yang sangat dia rindukan. Kegelisahan dimulai. Baik Arai maupun Ikal, keduanya tidak kuasa mengetahui isi dari surat itu. Setelah dibuka, hasilnya adalah Ikal diterima di Perguruan tinggi Sorbone, Prancis. Setelah perlahan mencocokkan dengan surat Arai, inilah jawaban dari mimpi-mimpi mereka. Kedua sang pemimpi ini diterima di Universitas yang sama. Tapi ini bukan akhir dari segalanya. Di sinilah perjuangan dari mimpi itu dimulai, dan siap melahirkan anak-anak mimpi berikutnya.",
                "https://hadi27.wordpress.com/sinopsis-novel-sang-pemimpi/"
        );

        tambahBerita(buku3, db);
        idBerita++;


    }




}
