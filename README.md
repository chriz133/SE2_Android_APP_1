# Introduction

This repository contains a simple Android application that communicates with a server to perform calculations based on a matriculation number. The application's primary features include sending the entered matriculation number to a server for processing or performing a local calculation based on it.

## Workflow

1. **User Input**: The user enters a matriculation number into the input field.
2. **Button Click**: When the user clicks either the "Send" or "Calculate" button, an action is triggered.
3. **Network Operation**:
   - When the "Send" button is clicked, the application sends the matriculation number to a server for processing.
   - When the "Calculate" button is clicked, the application performs a local calculation based on the matriculation number.
4. **Response Handling**:
   - If sending the matriculation number to the server, the application displays the server's response.
   - If performing a local calculation, the application displays the result of the calculation.

## Calculation

When the user clicks the "Calculate" button, the application performs an alternative checksum calculation based on the entered matriculation number. The calculation involves iterating through the digits of the matriculation number, alternating between adding and subtracting each digit. The calculation starts with a substraction. The result is then displayed to the user, indicating whether the sum is even or odd.

## Overview Serverinteraction

The application utilizes RxJava extensively for asynchronous operations. In the `NetworkManager` class, the `calculateResult` method returns an `Observable<String>`, encapsulating the network operation asynchronously. The `subscribeOn(Schedulers.io())` method ensures that the network operation is performed on a background thread to prevent UI thread blocking.

In the `MainActivity` class, the `observeOn(AndroidSchedulers.mainThread())` method is used to ensure that UI updates are executed on the main thread after receiving a response from the server. This implementation enhances the user experience by keeping the application responsive during network operations.

## Libraries Used

- **RxJava**: Utilized for handling asynchronous operations efficiently and implementing reactive programming principles.
- **AndroidX**: Used for UI components and compatibility with modern Android versions.

## Key Concepts

- **Observable Creation**: RxJava's `Observable.fromCallable()` method is utilized to create observables that emit the result of asynchronous tasks.
- **Schedulers**: The `subscribeOn` operator is used to specify the thread on which observables will operate, while the `observeOn` operator specifies the scheduler for observers.
- **Subscribe**: The `subscribe` method initiates the subscription to observables, allowing developers to handle emitted items, errors, and completion events.

## Why RxJava?

RxJava is used in this project to handle asynchronous operations efficiently. Here's why it's beneficial:

1. **Conciseness**: RxJava allows you to write asynchronous and event-based code in a concise and readable manner using reactive programming principles.

2. **Thread Management**: RxJava abstracts away the complexity of managing threads, making it easier to perform network operations on background threads and handle UI updates on the main thread.

3. **Error Handling**: RxJava provides powerful error handling mechanisms, making it easier to handle errors and exceptions in asynchronous operations.

4. **Composition**: RxJava provides operators that allow you to compose complex asynchronous operations from simpler ones, leading to more modular and maintainable code.

Overall, RxJava simplifies the process of handling asynchronous operations in Android applications, making it a popular choice for developers.


## Usage

To use this application, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
4. Enter a matriculation number in the input field and either send it to the server or calculate an alternative checksum locally.
