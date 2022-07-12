const jwt = require("jsonwebtoken");
const config = require("../config/auth/kej")

module.exports = (req, res, next) =>{
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    console.log(token);
    if(token == null){
        return res.sendStatus(401);
    }
    jwt.verify(token, config.secret, (err, user) =>{
        if(err){
            res.status(403).send({error:"You dont have permission for adding abonaments"});
        }
        if(user.role!=="admin"){
            res.status(403).send({error:"You dont have permission for adding abonaments"});
        }
        next();
    })
}
