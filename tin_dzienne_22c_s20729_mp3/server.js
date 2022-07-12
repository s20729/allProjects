const express = require("express");
const app = express();
const cors = require("cors");
const mongoose = require("mongoose");


var gymRouter = require('./routes/gymRoutes');
var peopleRouter = require('./routes/peopleRoutes')
var abonamentRouter = require('./routes/abonamentRoutes');

app.use(cors());
app.use(express.json());

const dbURI = 'mongodb+srv://s20729:Matsumauy@gym.iqoiv.mongodb.net/gym?retryWrites=true&w=majority';
mongoose.connect(dbURI).then((rezult) => console.log('connected to db')).catch((err) => console.log(err));

app.use(gymRouter);
app.use(peopleRouter);
app.use(abonamentRouter);




app.listen(3000, ()=>{
    console.log("server listen on port 3000");
})
