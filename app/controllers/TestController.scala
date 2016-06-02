package controllers

import play.api.mvc.{Action, Controller}


class TestController extends Controller{

  def test = Action {
    Ok(views.html.test())
  }

}
