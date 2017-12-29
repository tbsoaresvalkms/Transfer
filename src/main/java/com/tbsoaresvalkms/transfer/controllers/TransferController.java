package com.tbsoaresvalkms.transfer.controllers;

import com.tbsoaresvalkms.transfer.commands.TransferCreate;
import com.tbsoaresvalkms.transfer.commands.TransferFindAll;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferCreate create;
    private final TransferFindAll findAll;

    public TransferController(TransferCreate create, TransferFindAll findAll) {
        this.create = create;
        this.findAll = findAll;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDTO create(@Valid @RequestBody TransferDTO transferDTO) {
        return create.execute(transferDTO);
    }

    @GetMapping("/{account}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferDTO> index(@PathVariable Long account) {
        return findAll.execute(account);
    }
}
