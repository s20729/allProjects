const People = require('../models/people');
const Gym = require('../models/gyms');
const Abonament = require('../models/abonament');
const authUtil = require("../util/authUtils");
const jwt = require('jsonwebtoken')
const config = require("../config/auth/kej")

const login = (req, res, next) =>{
    const {numerTelefonu, password} = req.body;
    People.findOne({numerTelefonu})
    .then(people =>{
        if(!people){
            res.status(401).send({error: "Nieprawidlowy numer telefonu lub haslo"});
        }else if(authUtil.comparePasswords(password, people.password) === true){
            const token = jwt.sign(
                {
                    numerTelefonu: people.numerTelefonu,
                    userId: people._id,
                    role:people.role
                },
                config.secret, 
                {expiresIn:'1h'}
            )
            res.status(200).json({token, userId:people._id, role:people.role});
        }else{
            res.status(401).send({error: "Nieprawidlowy numer telefonu lub haslo 2"});
        }
    })
    .catch((err) =>{
      console.log(err);
    });
}
const logout = (req, res, next)=>{
    req.session.loggedUser = undefined;
}



module.exports ={
   login,
   logout
}