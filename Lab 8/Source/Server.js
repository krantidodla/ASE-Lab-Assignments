var express = require('express');
var app = express();
var cors=require('cors');
var request = require('request');
app.use(cors());

app.get('/calories', function (req, res,next) {
    var result = {
        "calo":[]
    };
    var z=req.query.name;
// console.log("z=")
// console.log(z);
    request("http://api.themoviedb.org/3/search/movie?api_key=62c648959dff042c455e5d6d7ed0413b&query="+z, function (error, response, body) {
        //Check for error
        if (error) {
            return console.log('Error:', error);
        }
        if (response.statusCode !== 200) {
            return console.log('Invalid Status Code Returned:', response.statusCode);
        }
        body = JSON.parse(body);
        // console.log(body)
        result.calo.push({"calories":body.results[0].popularity,"quantity":body.results[0].release_date});
        // console.log(result)
        request("https://kgsearch.googleapis.com/v1/entities:search?query="+z+"&key=AIzaSyA1Sy0t-uNoi-9pnR5ETJnTBGDNvoi6YhE&limit=1&indent=True",function (error1, response1, body1) {
            if (error1) {
                return console.log('Error:', error1);
            }
            if (response1.statusCode !== 200) {
                return console.log('Invalid Status Code Returned:', response1.statusCode);
            }
            bod = JSON.parse(body1);
            // console.log("bod=")
            // console.log(bod)
            result.calo.push({"link": bod.itemListElement[0].result.url,"desc":bod.itemListElement[0].result.detailedDescription.articleBody})
            // console("final result=")
            // console.log(result)
            res.contentType('application/json');
            res.write(JSON.stringify(result));
            // console.log(res)
            res.s
            res.end();
        });
    });
    // console.log(result);
})
var server = app.listen(8082,"127.0.0.1", function () {
    var host = server.address().address;
    var port = server.address().port;
    console.log("Example app listening at http://%s:%s", host, port);
})