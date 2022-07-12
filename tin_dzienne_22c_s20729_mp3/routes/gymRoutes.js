const express = require('express');
const router = express.Router();
const gymController = require('../controllers/gymController');
const isAuth = require("../middleware/isAuth")
const isAdmin = require('../middleware/isAdmin');

router.get('/gyms', gymController.gym_index);
  
router.post('/gyms', isAdmin, gymController.gym_create_post);

router.delete('/gym/delete/:id', isAdmin, gymController.gym_delete);

router.get('/gym/edit/:id', isAdmin, gymController.gym_edit_get);

router.post('/gym/edit/:id', isAdmin, gymController.gym_edit_post);


module.exports = router;