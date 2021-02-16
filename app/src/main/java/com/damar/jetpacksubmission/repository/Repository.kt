package com.damar.jetpacksubmission.repository

import android.util.Log
import com.damar.jetpacksubmission.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository constructor(
        private val localRepo: LocalRepo,
        private val remoteRepo: RemoteRepo,
        private val dispatcher: CoroutineDispatcher){
    init {
        println("Repository is Being Called")
    }

    suspend fun getTvTrending(): MutableList<TvTrending> {
        val result = mutableListOf<TvTrending>()
        CoroutineScope(dispatcher).launch {
            val local = localRepo.getTvTrending()
            if(local.isEmpty()){
                println("Get TV Trending Called : Online")
                val remote = remoteRepo.getTvTrending()
                result.addAll(remote)
                if(remote.isNotEmpty())localRepo.insertTvTrending(remote)
            }else{
                println("Get TV Trending Called : Local")
                result.addAll(local)
            }
        }
        return result
    }

    suspend fun getTvPopular(): MutableList<TvPopular> {
        val result = mutableListOf<TvPopular>()
        CoroutineScope(dispatcher).launch {
            val local = localRepo.getTvPopular()
            if(local.isEmpty()){
                println("Get TV Popular Called : Online")
                val remote = remoteRepo.getTvPopular()
                result.addAll(remote)
                if(remote.isNotEmpty()) localRepo.insertTvPopular(remote)
            }else{
                println("Get TV Popular Called : Local")
                result.addAll(local)
            }
        }.join()
        return result
    }

    suspend fun getMvTrending(): MutableList<MvTrending> {
        val result = mutableListOf<MvTrending>()
        CoroutineScope(dispatcher).launch {
            val local = localRepo.getMvTrending()
            if(local.isEmpty()){
                println("Get MV Trending Called : Online")
                val remote = remoteRepo.getMvTrending()
                result.addAll(remote)
                if(remote.isNotEmpty()) localRepo.insertMvTrending(remote)
            }else{
                println("Get MV Trending Called : Local")
                result.addAll(local)
            }
        }.join()
        return result
    }

    suspend fun getMvPopular(): MutableList<MvPopular> {
        val result = mutableListOf<MvPopular>()
        CoroutineScope(dispatcher).launch {
            val local = localRepo.getMvPopular()
            if(local.isEmpty()){
                println("Get MV Popular Called : Online")
                val remote = remoteRepo.getMvPopular()
                result.addAll(remote)
                if(remote.isNotEmpty()) localRepo.insertMvPopular(remote)
            }else{
                println("Get MV Popular Called : Local")
                result.addAll(local)
            }
        }.join()
        return result
    }

    suspend fun getDetailMv(id: Int): DetailMv?{
        return withContext(CoroutineScope(dispatcher).coroutineContext) {
            remoteRepo.getDetailMV(id)
        }
    }

    suspend fun getDetailTv(id: Int): DetailTv?{
        return withContext(CoroutineScope(dispatcher).coroutineContext) {
            remoteRepo.getDetailTV(id)
        }
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }

    companion object {
        private const val TAG = "Repository"
    }
}