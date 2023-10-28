package br.com.raulreis.instaapp.add

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface Add {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}