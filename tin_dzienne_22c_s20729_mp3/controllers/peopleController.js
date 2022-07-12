const People = require('../models/people');
const Gym = require('../models/gyms');
const Abonament = require('../models/abonament');
const authUtil = require("../util/authUtils");


const people_index = (req, res) =>{
    People.find()
    .then(result => res.json(result))
    .catch((err) =>{
      console.log(err);
    });
}
const people_create_post = (req, res, next)=>{
    const people = req.body;
    people.role="basic";
    people.password = authUtil.hashPassword(people.password);
    const peopleWithHashPassword = new People(people);
    peopleWithHashPassword.save()
    .catch((next));  
}

const people_delete = (req, res) =>{
    if(Abonament.find({people_idPeople : req.param('id')}) != null){
        const abonament = Abonament.find({people_idPeople : req.param('id')}).remove().exec().catch((err) =>{
            console.log(err);
        })
    }
    People.findByIdAndDelete(req.param('id'))
    .catch((err) =>{
        console.log(err);
    });
}

const people_edit_get = (req, res) =>{
    People.findById(req.param('id'))
    .then(result => res.json(result))
    .catch((err) =>{
        console.log(err);
    });
}

const people_edit_post = (req, res) =>{
    People.findByIdAndUpdate(req.param('id'), req.body)
    .catch((err) =>{
        console.log(err);
    })
}

const people_about_get =(req, res)=>{
    People.findById(req.param('id'))
    .catch((err) =>{
        console.log(err);
    });
}

module.exports ={
    people_index,
    people_create_post,
    people_delete,
    people_edit_get,
    people_edit_post,
    people_about_get
}