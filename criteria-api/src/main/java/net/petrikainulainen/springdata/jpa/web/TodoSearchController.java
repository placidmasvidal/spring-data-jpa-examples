package net.petrikainulainen.springdata.jpa.web;

import net.petrikainulainen.springdata.jpa.todo.TodoDTO;
import net.petrikainulainen.springdata.jpa.todo.TodoSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides the public API that is used to find todo entries by using
 * different search criteria.
 *
 * @author Petri Kainulainen
 */
@RestController
final class TodoSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoSearchController.class);

    private final TodoSearchService searchService;

    @Autowired
    public TodoSearchController(TodoSearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Finds todo entries whose title or description contains the given search term. This
     * search is case insensitive.
     * @param searchTerm    The used search term.
     * @param pageRequest   The information of the requested page
     * @return
     */
    @RequestMapping(value = "/api/todo/search", method = RequestMethod.GET)
    public Page<TodoDTO> findBySearchTerm(@RequestParam("searchTerm") String searchTerm, Pageable pageRequest) {
        LOGGER.info("Finding todo entries by search term: {} and page request: {}", searchTerm, pageRequest);

        Page<TodoDTO> searchResultPage = searchService.findBySearchTerm(searchTerm, pageRequest);
        LOGGER.info("Found {} todo entries. Returned page {} contains {} todo entries",
                searchResultPage.getTotalElements(),
                searchResultPage.getNumber(),
                searchResultPage.getNumberOfElements()
        );

        return searchResultPage;
    }
}
