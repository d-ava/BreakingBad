package com.example.breakingbad.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.model.BBCharacter

private const val STARTING_INDEX = 0

class BBPagingSource():PagingSource<Int, BBCharacter>() {


    override fun getRefreshKey(state: PagingState<Int, BBCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BBCharacter> {
        val offset:Int = params.key ?: STARTING_INDEX
        return try {
            val response = NetworkClient.bbCharactersApi.getBBCharacters(limit = 10, offset = offset)
            val body = response.body()

            if (response.isSuccessful && body !=null){
                var prevOffset:Int? = null
                var nextOffset:Int? = null

                if (body.isNotEmpty()){
                    nextOffset = offset+10
                }
//                if (offset != STARTING_INDEX)
                    prevOffset = offset -10
                    LoadResult.Page(
                        data = body, prevKey = prevOffset, nextKey = nextOffset
                    )
                }else{
                    LoadResult.Error(Throwable())
                }

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}