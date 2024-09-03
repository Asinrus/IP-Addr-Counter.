import os
import random
import sys

def generate_ip():
    """Generate a random IP address."""
    return f"{random.randint(0, 255)}.{random.randint(0, 255)}.{random.randint(0, 255)}.{random.randint(0, 255)}"

def generate_ips_file(target_size_gb):
    """Generate a file with random IP addresses of approximately `target_size_gb` gigabytes."""
    target_size = target_size_gb * 1024 * 1024 * 1024  # Convert GB to bytes
    file_path = f"ips_{target_size_gb}GB.txt"

    with open(file_path, 'w') as file:
        while os.path.getsize(file_path) < target_size:
            ip = generate_ip()
            file.write(ip + '\n')

    print(f"Generated file {file_path} with size approximately {target_size_gb} GB.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_ips.py <size_in_gb>")
        sys.exit(1)

    size_in_gb = int(sys.argv[1])
    generate_ips_file(size_in_gb)