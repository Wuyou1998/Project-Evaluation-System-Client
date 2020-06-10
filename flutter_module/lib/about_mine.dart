import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AboutMine extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    double width = MediaQuery.of(context).size.width;
    double height = MediaQuery.of(context).size.height;
    return Center(
        child: Card(
      child: Container(
        width: 200,
        height: 300,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Container(
                width: 50,
                height: 50,
                child:
                    Card(elevation: 4, child: Image.asset('image/avatar.jpg'))),
            Container(
              height: 5,
            ),
            Text('吴优'),
            Container(
              height: 3,
            ),
            Text('物联网工程20届应届毕业生'),
          ],
        ),
      ),
    ));
  }
}
