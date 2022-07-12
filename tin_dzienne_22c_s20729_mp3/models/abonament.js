const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const abonamentSchema = new Schema({
    silownia_idSilowni : {
        type: Schema.Types.ObjectId,
        ref:'Gym'
    },
    people_idPeople : {
        type:Schema.Types.ObjectId,
        ref:'People'
    },
    od : {
        type:Date,
        required:true
    },
    do : {
        type:Date,
        required:false
    },
    cenaWMiesiac: {
        type:Number,
        require:true
    }

}, {timestamps: true});

const Abonament = mongoose.model('Abonament', abonamentSchema);

module.exports = Abonament;
