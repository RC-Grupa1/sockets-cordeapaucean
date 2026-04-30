import socket

HOST = "0.0.0.0"
PORT = 5000


def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Permite repornirea rapidă a serverului pe același port
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    # Timeout ca să poți opri serverul cu Ctrl + C chiar și când așteaptă client
    server_socket.settimeout(1.0)

    try:
        server_socket.bind((HOST, PORT))
        server_socket.listen(1)

        print(f"Server TCP pornit pe portul {PORT}.")
        print("Astept conexiune de la client...")
        print("Pentru oprire manuala: Ctrl + C")
        print("Pentru oprire din chat: scrie exit")

        conn = None
        addr = None

        while True:
            try:
                conn, addr = server_socket.accept()
                break
            except socket.timeout:
                continue

        print(f"Client conectat: {addr}")

        with conn:
            while True:
                data = conn.recv(1024)

                if not data:
                    print("Clientul s-a deconectat.")
                    break

                mesaj_client = data.decode("utf-8").strip()
                print(f"Client: {mesaj_client}")

                if mesaj_client.lower() == "exit":
                    print("Clientul a inchis conversatia.")
                    break

                mesaj_server = input("Server: ")

                conn.sendall((mesaj_server + "\n").encode("utf-8"))

                if mesaj_server.lower() == "exit":
                    print("Serverul a inchis conversatia.")
                    break

    except KeyboardInterrupt:
        print("\nServer oprit manual cu Ctrl + C.")

    except OSError as e:
        print(f"Eroare la server: {e}")

    finally:
        server_socket.close()
        print("Socket TCP inchis.")


if __name__ == "__main__":
    main()