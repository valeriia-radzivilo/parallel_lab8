import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Matrix Multiplication',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Matrix Multiplication Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, this.title}) : super(key: key);

  final String? title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title!),
      ),
      body: Column(
        children: [Text(
          'Welcome to Matrix Multiplication App',
        ),
        ElevatedButton(onPressed:(){},
        child: Text('Generate random matrix'),),
        ElevatedButton(onPressed:()async {
          var url = Uri.parse('http://localhost:8080/multiply');
          var body = jsonEncode({
            'firstMatrix': [[1, 2], [3, 4]],
            'secondMatrix': [[5, 6], [7, 8]],
            'threadCount': 4
          });
          var response = await http.post(url, body: body, headers: {'Content-Type': 'application/json'});
          if (response.statusCode == 200) {
            print('Result matrix: ${jsonDecode(response.body)}');
          } else {
            print('Request failed with status: ${response.statusCode}.');
          }
        }
        child: Text('Multiply matrix'),),

        ]
      ),
    );
  }
}