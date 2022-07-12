const express = require('express');
const router = express.Router();
const abonamentController = require('../controllers/abonamentController');
const isAuth = require("../middleware/isAuth")
const isAdmin = require('../middleware/isAdmin');

router.get('/abonaments', abonamentController.abonament_index);

router.post('/abonament/add', isAdmin, abonamentController.abonament_create_post);

router.get('/abonament/delete/:id', isAuth, abonamentController.abonament_delete);

router.get('/abonament/edit/:id', isAdmin, abonamentController.abonament_edit_get);

router.post('/abonament/edit/:id', isAdmin, abonamentController.abonament_edit_post);

router.get('/abonament/info/:id', isAuth, abonamentController.abonament_info_get);

module.exports = router;