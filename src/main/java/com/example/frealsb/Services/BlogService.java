package com.example.frealsb.Services;

import com.example.frealsb.Entities.Blog;
import com.example.frealsb.Entities.User;
import com.example.frealsb.Repositories.BlogRepository;
import com.example.frealsb.RequestEntities.RequestBlog;
import com.example.frealsb.Services.Interface.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogService implements IBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> getBlogs(String s, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogRepository.findAllBy(s, pageable);
    }

    @Override
    public Blog getBlog(String id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Blog addBlog(RequestBlog req, User user) {
        return blogRepository.saveAndFlush(req.toAddData(user));
    }

    @Override
    public Blog updateBlog(RequestBlog req) {
        return blogRepository.saveAndFlush(req.toUpdateData());
    }

    @Override
    public Blog deleteBlog(String id) {
        Blog blog = getBlog(id);
        blog.setDeletedAt(new Date());
        return blogRepository.saveAndFlush(blog);
    }
}
