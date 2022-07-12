const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const gymsSchema = new Schema({
    adres : {
        type: String,
        require:true
    },
    liczbaUczestnikow : {
        type:Number,
        required: true
    },
    nazwa : {
        type:String,
        required:true
    },
    trzymac : {
        type:String,
        required:true
    }

}, {timestamps: true});

const Gym = mongoose.model('Gym', gymsSchema);

module.exports = Gym;
