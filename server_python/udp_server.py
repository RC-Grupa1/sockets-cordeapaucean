import socket

HOST = "0.0.0.0"
PORT = 5001


def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Timeout ca sa poti opri serverul cu Ctrl + C
    server_socket.settimeout(1.0)

    try:
        server_socket.bind((HOST, PORT))

        print(f"Server UDP pornit pe portul {PORT}.")
        print("Astept primul mesaj de la client...")
        print("Pentru oprire manuala: Ctrl + C")
        print("Pentru oprire din chat: scrie exit")

        while True:
            try:
                data, client_address = server_socket.recvfrom(1024)
            except socket.timeout:
                continue

            mesaj_client = data.decode("utf-8").strip()
            print(f"Client {client_address}: {mesaj_client}")

            if mesaj_client.lower() == "exit":
                print("Clientul a inchis conversatia.")
                break

            mesaj_server = input("Server: ")

            server_socket.sendto(
                mesaj_server.encode("utf-8"),
                client_address
            )

            if mesaj_server.lower() == "exit":
                print("Serverul a inchis conversatia.")
                break

    except KeyboardInterrupt:
        print("\nServer UDP oprit manual cu Ctrl + C.")

    except OSError as e:
        print(f"Eroare la serverul UDP: {e}")

    finally:
        server_socket.close()
        print("Socket UDP inchis.")


if __name__ == "__main__":
    main()