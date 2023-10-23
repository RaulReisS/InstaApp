package br.com.raulreis.instaapp.profile.data

import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: ProfileCache<UserAuth>,
    private val postsCache: ProfileCache<List<Post>>
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createFromUser(): ProfileDataSource {
        if(profileCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return ProfileFakeRemoteDataSource()
    }

    fun createFromPosts(): ProfileDataSource {
        if(postsCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return ProfileFakeRemoteDataSource()
    }

}