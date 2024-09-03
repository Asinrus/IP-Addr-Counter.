import sys

def count_unique_strings(filename):
    unique_strings = set()

    with open(filename, 'r') as file:
        for line in file:
            line = line.strip()
            if line:
                unique_strings.add(line)

    return len(unique_strings)

# Check if the filename is provided as a command-line argument
if len(sys.argv) < 2:
    print("Usage: python script.py <filename>")
    sys.exit(1)

filename = sys.argv[1]
count = count_unique_strings(filename)
print(f'The file {filename} contains {count} unique strings.')