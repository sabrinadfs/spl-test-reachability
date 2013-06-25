import sys

def process(data):
    lines = data.readlines()
    total = len(lines)
    i = 0

    while i < total - 1:
        line = lines[i]
        with open(lines[i][:-1] + ".scope",'w') as outfile:
            i += 1 
            line = lines[i]
            while "----" not in line:
                outfile.write(line)
                i += 1
                line = lines[i]
        i += 1

def main():
    input = sys.argv[1]
    with open(input) as data:
        process(data)

main()
