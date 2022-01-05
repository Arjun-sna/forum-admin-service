package com.learn.admin.dto.role;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
public class AssignMembersRequest {
    @NotNull
    @Size(min = 1)
    private ArrayList<Integer> memberIds;
}
