const mongoose = require('mongoose');
const Schema = mongoose.Schema;
var uniqueValidator = require('mongoose-unique-validator');

const peoplesSchema = new Schema({
    imie : {
        type: String,
        require:true
    },
    nazwisko : {
        type:String,
        required: true
    },
    ulgowy : {
        type:String,
        required:true
    },
    numerTelefonu : {
        type:Number,
        required:false,
        unique:[true, "Person with such telephone number already exists"]
    },
    password:{
        type:String,
        required:true
    },
    role :{
        type:String,
        required:true
    }

}, {timestamps: true});

peoplesSchema.plugin(uniqueValidator, { message: 'People with such telephone number alreadt exists' });
const People = mongoose.model('People', peoplesSchema);

module.exports = People;