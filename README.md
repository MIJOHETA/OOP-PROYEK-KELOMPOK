# TuneTix 🎵🎫

Aplikasi desktop untuk simulasi sistem pemesanan tiket konser yang dibangun dengan Java dan JavaFX.

## 📋 Deskripsi

**TuneTix** adalah aplikasi desktop yang mensimulasikan sistem pemesanan tiket konser secara lengkap. Pengguna dapat melihat daftar konser, memesan tiket dengan berbagai kategori, melakukan simulasi pembayaran melalui transfer bank virtual, memverifikasi pembayaran, dan menerima tiket dalam bentuk digital.

## ✨ Fitur Utama

- 🎪 **Daftar Konser**: Lihat berbagai konser yang tersedia
- 🎟️ **Pemesanan Tiket**: Pilih konser dan kategori tiket (REGULAR, VIP, VVIP)
- 💳 **Simulasi Pembayaran**: Transfer bank virtual dengan nomor Virtual Account
- ✅ **Verifikasi Pembayaran**: Konfirmasi pembayaran dengan kode booking
- 📄 **Tiket Digital**: Cetak tiket ke konsol dan simpan sebagai file `.txt`
- 👤 **Manajemen Pengguna**: Login sederhana dengan nama pengguna

## 🛠️ Teknologi yang Digunakan

- **Java** (JDK 11+)
- **JavaFX** untuk antarmuka grafis
- **Object-Oriented Programming** dengan clean architecture

## 📁 Struktur Proyek

```
TuneTix/
├── TuneTixApplication.java    # Kelas utama aplikasi
├── User.java                  # Model data pengguna
├── Event.java                 # Model data konser/acara
├── Venue.java                 # Model data lokasi acara
├── Booking.java               # Model data pemesanan
├── Payment.java               # Model data pembayaran
├── Ticket.java                # Model data tiket
├── Bank.java                  # Enum pilihan bank
├── TicketCategory.java        # Enum kategori tiket
├── EventStatus.java           # Enum status event
├── PaymentStatus.java         # Enum status pembayaran
└── BookingStatus.java         # Enum status booking
```

## 🚀 Cara Menjalankan

### Prasyarat

1. **Java Development Kit (JDK) 11+**
2. **JavaFX SDK** - Download dari [Gluon](https://gluonhq.com/products/javafx/)

### Instalasi & Eksekusi

1. **Clone atau Download**
   ```bash
   # Pastikan semua file .java dalam satu direktori
   ```

2. **Kompilasi**
   ```bash
   javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls *.java
   ```

3. **Jalankan**
   ```bash
   java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls TuneTixApplication
   ```

   > **Catatan**: Ganti `path/to/javafx-sdk/lib` dengan path sebenarnya ke folder lib JavaFX SDK di sistem Anda.

## 📱 Cara Penggunaan

### 1. Login
- Masukkan nama lengkap Anda
- Klik tombol "Mulai"

### 2. Beli Tiket
- Pilih konser dari dropdown
- Pilih kategori tiket (REGULAR/VIP/VVIP)
- Lihat harga dan ketersediaan tiket
- Klik "Lanjut ke Pembayaran"

### 3. Pembayaran
- Pilih bank untuk transfer virtual
- Catat Nomor Virtual Account yang diberikan
- Simpan Kode Booking Anda
- Klik "OK" untuk menyelesaikan

### 4. Verifikasi Pembayaran
- Kembali ke menu utama
- Pilih "Verifikasi Pembayaran"
- Masukkan Kode Booking
- Klik "Verifikasi"

### 5. Tiket Terbit
- Tiket akan tercetak di terminal/konsol
- File tiket `.txt` akan dibuat di direktori aplikasi
- Gunakan "Lihat Tiket Saya" untuk melihat koleksi tiket

## 🎯 Komponen Utama

### Model Classes
- **User**: Data pengguna yang login
- **Event**: Informasi lengkap konser (nama, venue, tanggal, harga, ketersediaan)
- **Venue**: Lokasi penyelenggaraan konser
- **Booking**: Data pesanan sementara dengan kode booking unik
- **Payment**: Pengelolaan pembayaran dan Virtual Account
- **Ticket**: Tiket final setelah pembayaran terverifikasi

### Enumerations
- **Bank**: Pilihan bank untuk pembayaran (BCA, BNI, BRI, Mandiri)
- **TicketCategory**: Kategori tiket (REGULAR, VIP, VVIP)
- **EventStatus**: Status ketersediaan (AVAILABLE, SOLD_OUT)
- **PaymentStatus**: Status pembayaran (PENDING, SUCCESS, FAILED)

## 📝 Contoh Output

### Console Output
```
=== TIKET KONSER ===
Nama Pemegang: John Doe
Event: Rock Concert 2024
Venue: Jakarta Convention Center, Jakarta
Tanggal: 2024-12-25
Kategori: VIP
Harga: Rp 500,000
Kode Tiket: TICK001
===================
```

### File Output
File `Tiket_John_Doe_Rock_Concert_2024.txt` akan dibuat dengan detail tiket yang sama.

## 🔧 Kustomisasi

Anda dapat dengan mudah menambahkan:
- Konser baru dengan memodifikasi method `initializeEvents()`
- Bank baru di enum `Bank.java`
- Kategori tiket baru di enum `TicketCategory.java`
- Fitur tambahan seperti diskon, membership, dll.

## 🤝 Kontribusi

Feel free to fork this project and submit pull requests for improvements!

## 📄 Lisensi

This project is open source and available under the [MIT License](LICENSE).

## 📞 Kontak

Jika ada pertanyaan atau saran, silakan buat issue di repository ini.

---

**Happy Concert Ticketing! 🎉🎵**
