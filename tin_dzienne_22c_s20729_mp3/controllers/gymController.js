const Gym = require('../models/gyms');
const Abonament = require('../models/abonament');


const gym_index = (req, res)=>{
    Gym.find()
    .then(result => res.json(result))
    .catch((err) =>{
      console.log(err);
    });
} 

const gym_create_post = (req, res)=>{
    const gym = new Gym(req.body);
    gym.save().then(()=>{
       return res.ok;
    })
    .catch((err) =>{
      console.log(err);
    });  
}

const gym_delete = (req, res) =>{
    if(Abonament.find({silownia_idSilowni : req.param('id')}) != null){
        const abonament = Abonament.find({silownia_idSilowni : req.param('id')}).remove().exec().catch((err) =>{
            console.log(err);
        })
    }
    Gym.findByIdAndDelete(req.param('id'))
    .catch((err) =>{
        console.log(err);
    });
}

const gym_edit_get = (req, res) =>{
    Gym.findById(req.param('id'))
    .then(result => res.json(result))
    .catch((err) =>{
      console.log(err);
    });
}

const gym_edit_post = (req, res) =>{
    Gym.findByIdAndUpdate(req.param('id'), req.body)
    .then(() =>{
        res.ok;
    })
    .catch((err) =>{
        console.log(err);
    })
}

module.exports = {
    gym_index,
    gym_create_post,
    gym_delete,
    gym_edit_get,
    gym_edit_post
}