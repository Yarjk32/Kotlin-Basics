package bot

fun greet() {
    println("Hello! My name is Haha.")
    println("I was created in 2022.")
}

fun remindName() {
    println("Please, remind me your name.")
    val username = readLine()
    print("What a great name you have, ")
    print(username)
    println("!")
}

fun guessAge() {
    println("Let me guess your age.")
    println("Enter remainders of dividing your age by 3, 5 and 7.")
    val rem3 = readLine()!!.toInt()
    val rem5 = readLine()!!.toInt()
    val rem7 = readLine()!!.toInt()
    val yourAge = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
    println("Your age is $yourAge. That is a good time to start starting!")
}

fun count() {
    println("Now I will prove to you that I can count to any number you want.")
    val countTo = readLine()!!.toInt()
    for (i in 0..countTo) println("$i!")
}

fun captcha() {
    println("Let's check if you are a human.")
    println("Select the row with two words. Should you enter only one digit?")
    println("1. Hello!")
    println("2. Cat.")
    println("3. Nice⠀weather.")
    println("4. Amogus.")
    println("5. Bye!")
    var answer = readLine()!!.toInt()
    while (answer != 3) {
        println("You are a bot!")
        answer = readLine()!!.toInt()
    }
}

fun end() {
    println("Congratulations, have a nice day!") // Do not change this text
}

fun main() {
    greet()
    remindName()
    guessAge()
    count()
    captcha()
    end()
}
