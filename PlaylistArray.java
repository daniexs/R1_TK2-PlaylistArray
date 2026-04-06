// Team Assignment Data Structures and Algorithm Analysis Session 08
// 2902742035 - BENI SAPRULAH
// 2902694452 - MUHAMMAD PERMADANI
// 2902702990 - INTAN PRATIWI
// 2902697132 - ARAFAH NUR SOLEHAH
// 2902698186 - ARIFIANTO

import java.util.Scanner;

class Lagu {
    // atribut private (enkapsulasi)
    private String judul;
    private String artis;
    private double durasi;

    // constructor
    public Lagu(String judul, String artis, double durasi) {
        this.judul = judul;
        this.artis = artis;
        setDurasi(durasi); // pakai setter agar validasi berjalan
    }

    // getter dan setter

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        if (judul == null || judul.isBlank())
            throw new IllegalArgumentException("silahkan isi judul lagu");
        this.judul = judul;
    }

    public String getArtis() {
        return artis;
    }

    public void setArtis(String artis) {
        if (artis == null || artis.isBlank())
            throw new IllegalArgumentException("Silahkan isi nama artis");
        this.artis = artis;
    }

    public double getDurasi() {
        return durasi;
    }

    public void setDurasi(double durasi) {
        if (durasi <= 0)
            throw new IllegalArgumentException("minimal durasi 1");
        this.durasi = durasi;
    }

    // Method tampilkanInfo()
    public void tampilkanInfo() {
        System.out.printf("%s - %s (%.2f menit)%n", judul, artis, durasi);
    }
}

public class PlaylistArray {
    Lagu[] playlist = new Lagu[10];
    int jumlahLagu = 0;

    int cariIndex(String judulLagu) {
        for (int i = 0; i < jumlahLagu; i++) {
            if (playlist[i].getJudul().equalsIgnoreCase(judulLagu)) {
                return i;
            }
        }
        return -1;
    }

    public static void tekanEnter(Scanner input, boolean flag) {
        System.out.println("\nTekan ENTER untuk kembali ke menu...");
        input.nextLine();
        if (!flag) {
            input.nextLine();
        }
        System.out.println("Kembali...");
        delay(800);
    }

    public static void tekanEnter(Scanner input) {
        tekanEnter(input, false);
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void tampilkanSemuaLagu() {
        // validasi playlist kosong
        if (jumlahLagu == 0) {
            System.out.println("Playlist masih kosong.");
            return;
        }

        System.out.println("--- Daftar lagu (" + jumlahLagu + " lagu) ---");
        // tampilkan semua lagu
        for (int i = 0; i < jumlahLagu; i++) {
            System.out.printf((i + 1) + ". ");
            // tampilkan informasi lagu
            playlist[i].tampilkanInfo();
        }
    }

    void tambahLagu(Scanner input) {
        // validasi jumlah lagu
        if (jumlahLagu >= playlist.length) {
            System.out.println("Playlist penuh (maksimal " + playlist.length + " lagu).");
            return;
        }

        input.nextLine();

        System.out.print("Judul lagu : ");
        String judul = input.nextLine();
        System.out.print("Nama artis : ");
        String artis = input.nextLine();
        System.out.print("Durasi (menit desimal, mis. 3.5 = 3 menit 30 dtk): ");
        double durasi = input.nextDouble();

        try {
            // buat objek lagu baru
            Lagu baru = new Lagu(judul, artis, durasi);
            // tambahkan lagu ke playlist
            playlist[jumlahLagu] = baru;
            // increment jumlah lagu
            jumlahLagu++;
            System.out.println("Lagu \"" + baru.getJudul() + "\" berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah lagu: " + e.getMessage());
        }
    }

    void hapusLagu(String judulLagu) {
        // 1. Cari index lagu terlebih dahulu
        int index = cariIndex(judulLagu);

        // 2. Jika tidak ditemukan, keluar
        if (index == -1) {
            return;
        }

        // 3. Tampilkan info lagu yang akan dihapus
        System.out.println(" Menghapus: " + playlist[index].getJudul());

        // 4. Geser semua elemen setelah index ke kiri (agar array rapat)
        for (int i = index; i < jumlahLagu - 1; i++) {
            playlist[i] = playlist[i + 1]; // Shift left
        }

        // 5. Set elemen terakhir menjadi null & kurangi counter
        playlist[jumlahLagu - 1] = null;
        jumlahLagu--;

        System.out.println("Lagu berhasil dihapus! Total lagu: " + jumlahLagu);
        System.out.println("---");
    }

    void cariLagu(String judulLagu) {
        // Linear search: cek satu per satu dari index 0
        for (int i = 0; i < jumlahLagu; i++) {
            // Case insensitive search (huruf besar/kecil diabaikan)
            if (playlist[i].getJudul().equalsIgnoreCase(judulLagu)) {
                System.out.println("Lagu ditemukan di posisi " + (i + 1));
                playlist[i].tampilkanInfo();
                return;
            }
        }
        // Tidak ditemukan
        System.out.println("Lagu '" + judulLagu + "' tidak ditemukan!");
    }

    void urutkanLaguBerdasarkanDurasi() {
        if (jumlahLagu == 0) {
            System.out.println("Playlist kosong!");
            return;
        }

        System.out.println("\n=== Sebelum Sorting ===");
        tampilkanSemuaLagu();

        // Bubble sort pakai getter
        for (int i = 0; i < jumlahLagu - 1; i++) {
            for (int j = 0; j < jumlahLagu - i - 1; j++) {

                if (playlist[j].getDurasi() > playlist[j + 1].getDurasi()) {

                    Lagu temp = playlist[j];
                    playlist[j] = playlist[j + 1];
                    playlist[j + 1] = temp;
                }
            }
        }
        System.out.println("\n=== Setelah Sorting (Ascending Durasi) ===");
        tampilkanSemuaLagu();
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int pilihanUtama;
        PlaylistArray p = new PlaylistArray();

        do {
            System.out.println("\n=== MENU PLAYLIST MUSIK ===");
            System.out.println("1. Tampilkan semua lagu");
            System.out.println("2. Tambah lagu baru");
            System.out.println("3. Hapus lagu berdasarkan judul");
            System.out.println("4. Cari lagu berdasarkan judul");
            System.out.println("5. Urutkan berdasarkan durasi");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            pilihanUtama = input.nextInt();

            switch (pilihanUtama) {
                case 1:
                    p.tampilkanSemuaLagu();
                    tekanEnter(input);
                    break;

                case 2:
                    p.tambahLagu(input);
                    tekanEnter(input);
                    break;

                case 3:
                    input.nextLine();
                    System.out.print("Masukkan judul lagu yang ingin dihapus: ");
                    String judulHapus = input.nextLine();
                    p.hapusLagu(judulHapus);
                    tekanEnter(input, true);
                    break;

                case 4:
                    input.nextLine();
                    System.out.print("Masukkan judul lagu yang dicari: ");
                    String judulCari = input.nextLine();
                    p.cariLagu(judulCari);
                    tekanEnter(input, true);
                    break;

                case 5:
                    p.urutkanLaguBerdasarkanDurasi();
                    tekanEnter(input);
                    break;

                case 6:
                    System.out.println("Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihanUtama != 6);

        input.close();
    }

}
