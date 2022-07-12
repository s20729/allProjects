const Abonament = require('../models/abonament');
const Gym = require('../models/gyms');
const People = require('../models/people');

const abonament_index = (req, res)=>{
    Abonament.find()
    .populate("silownia_idSilowni")
    .populate("people_idPeople")
    .then(result => res.json(result))
    .catch((err) =>{
      console.log(err);
    });
}

const abonament_create_post = (req, res)=>{
  People.findOne({numerTelefonu: req.param('numerTelefonu'), nazwisko:req.param('nazwisko')}, (error, people) =>{
    if(error){
      console.log(error);
    }else{
      Gym.findOne({nazwa: req.param('nazwa'), adres: req.param('adres')}, (er, gym) =>{
        if(er){
          console.log(er);
        }else if( people !== null &&  gym !==null){
          const abonament = new Abonament({
            silownia_idSilowni : gym._id.valueOf(),
            people_idPeople: people._id.valueOf(),
            od: req.param('od'),
            do: req.param('do'),
            cenaWMiesiac: req.param('cenaWMiesiac')
          });
          abonament.save()
          .catch((err) =>{
            console.log(err);
          });
          res.status(200).send(200);
        }else if (people===null && gym !==null){
          res.status(500).send({error:"People with such name and telephone number doesnt exist"});
        }else if(gym ===null && people !== null){
          res.status(500).send({error:"Gym with such adres and name  doesnt exist"});
        }else{
          res.status(500).send({error:"Gym and people with such data doesnt exist"});
        }
      })
    }
  });
}
const abonament_delete = (req, res) =>{
  Abonament.findByIdAndDelete(req.param('id'))
  .catch((err) =>{
      console.log(err);
  });
}

const abonament_edit_get = (req, res) =>{
  Abonament.findById(req.param('id'))
  .then(result => res.json(result))
  .catch((err) =>{
      console.log(err);
  });
}

const abonament_edit_post = (req, res) =>{
  Abonament.findByIdAndUpdate(req.param('id'), req.body)
  .catch((err) =>{
      console.log(err);
  })
}

const abonament_info_get = (req, res) =>{
  Abonament.findById(req.param('id'))
  .populate("silownia_idSilowni")
  .populate("people_idPeople")
  .then(result => res.json(result))
  .catch((err) =>{
      console.log(err);
  });
}


module.exports = {
    abonament_index,
    abonament_create_post,
    abonament_delete,
    abonament_edit_get,
    abonament_edit_post,
    abonament_info_get
}