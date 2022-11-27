
def factorial(n):
    if n == 0:
        return 1
    else:
        return n * factorial(n-1)

sum=0

for i in range(1,170):
    sum+=(1)**i/(factorial(i)**(1/i))

print(sum)